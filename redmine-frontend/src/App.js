import React, {Component} from 'react';

const $ = window.$;

class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      logged: false,
      token: null,
      issues: [],
    }

    this.handleLogin = this.handleLogin.bind(this);
    this.handleLogout = this.handleLogout.bind(this);
  }

  handleLogin(credentials) {
    console.log('Application logged in: ', credentials)
    this.setState({logged: true, token: credentials})
  }

  handleLogout(event) {
    this.setState({logged: false})
  }

  render() {
    return (
      <div>
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark justify-content-between">
          <a className="navbar-brand" href="#">LDAP Redmine</a>
          {this.state.logged &&
          <button className="btn btn-danger" onClick={this.handleLogout}>Log out</button>}
        </nav>
        <div className="container">
          <div className="p-3 row justify-content-md-center">
            {this.state.logged ?
              <Issues token={this.state.token}/> :
              <Logger onLogin={this.handleLogin}/>}
          </div>
        </div>
      </div>
    );
  }
}

class Logger extends Component {
  constructor(props) {
    super(props);

    this.state = {
      username: '',
      password: '',
      error: false
    };

    this.handleUsernameChange = this.handleUsernameChange.bind(this);
    this.handlePasswordChange = this.handlePasswordChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleUsernameChange(event) {
    event.persist()
    this.setState({username: event.target.value});
  }

  handlePasswordChange(event) {
    event.persist()
    this.setState({password: event.target.value});
  }

  handleSubmit(event) {
    console.log('Attempting to login')
    let credentials = btoa(`${this.state.username}:${this.state.password}`)
    fetch('http://localhost:8080/api/login', {method: 'POST', headers: {'Authorization': `Basic ${credentials}`}})
      .then(response => {
        if (!response.ok) {
          throw Error("Invalid login")
        }

        console.log('Successful login: ', response.headers)
        this.props.onLogin(response.headers.get('Authorization'))
      })
      .catch(error => {
        console.log('Failed login', error.toString())
        this.setState({error: true})
      })
    event.preventDefault();
  }

  render() {
    return (
      <div className="w-25">
        {this.state.error &&
        <div className="alert alert-danger" role="alert">
          Error on login! Invalid credentials?
        </div>
        }
        <form onSubmit={this.handleSubmit}>
          <div className="form-group">
            <label htmlFor="inputUsername">Username</label>
            <input type="username" className="form-control" id="inputUsername" value={this.state.username}
                   onChange={this.handleUsernameChange}/>
            <small id="usernameHelp" className="form-text text-muted">Provide your username</small>
          </div>
          <div className="form-group">
            <label htmlFor="inputPassword">Password</label>
            <input type="password" className="form-control" id="inputPassword" value={this.state.password}
                   onChange={this.handlePasswordChange}/>
            <small id="passwordHelp" className="form-text text-muted">Provide your password</small>
          </div>
          <button type="submit" className="btn btn-primary">Submit</button>
        </form>
      </div>
    )
  }
}

class Issues extends Component {
  constructor(props) {
    super(props);

    this.state = {
      issues: []
    }

    this.onUpdate = this.onUpdate.bind(this);
  }

  componentDidMount() {
    fetch('http://localhost:8080/api/issues', {
      method: 'GET',
      headers: {'Authorization': `Bearer ${this.props.token}`}
    })
      .then(res => res.json())
      .then((data) => {
        this.setState({issues: data})
      })
      .catch(console.log)
  }

  onUpdate() {
    fetch('http://localhost:8080/api/issues', {
      method: 'GET',
      headers: {'Authorization': `Bearer ${this.props.token}`}
    })
      .then(res => res.json())
      .then((data) => {
        this.setState({issues: data})
      })
      .catch(console.log)
  }

  render() {
    return (
      <div>
        <div className="row justify-content-between align-items-center px-3">
          <h2>Issues</h2>
          <AddIssue onUpdate={this.onUpdate} token={this.props.token}/>
        </div>
        <table className="table">
          <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Tracker</th>
            <th scope="col">Subject</th>
            <th scope="col">Status</th>
            <th scope="col">Priority</th>
            <th scope="col">Assignee</th>
            <th scope="col">Category</th>
            <th scope="col">Due Date</th>
            <th scope="col">Estimate</th>
            <th scope="col">Actions</th>
          </tr>
          </thead>
          <tbody>
          {
            this.state.issues.map((issue, index) => (
              <Issue key={issue.id} issue={issue} index={index + 1} onUpdate={this.onUpdate} token={this.props.token}/>
            ))
          }
          </tbody>
        </table>
      </div>
    )
  }
}

class AddIssue extends Component {
  constructor(props) {
    super(props);

    this.state = {
      tracker: "",
      subject: "",
      description: "",
      status: "",
      priority: "",
      assignee: "",
      category: "",
      startDate: "",
      dueDate: "",
      estimatedTime: "",
    }

    this.onShowEdit = this.onShowEdit.bind(this);
    this.onTrackerUpdate = this.onTrackerUpdate.bind(this);
    this.onSubjectUpdate = this.onSubjectUpdate.bind(this);
    this.onDescriptionUpdate = this.onDescriptionUpdate.bind(this);
    this.onStatusUpdate = this.onStatusUpdate.bind(this);
    this.onPriorityUpdate = this.onPriorityUpdate.bind(this);
    this.onAssigneeUpdate = this.onAssigneeUpdate.bind(this);
    this.onCategoryUpdate = this.onCategoryUpdate.bind(this);
    this.onStartDateUpdate = this.onStartDateUpdate.bind(this);
    this.onDueDateUpdate = this.onDueDateUpdate.bind(this);
    this.onEstimateUpdate = this.onEstimateUpdate.bind(this);
    this.onEdit = this.onEdit.bind(this);
  }

  onShowEdit() {
    this.setState({
      tracker: "",
      subject: "",
      description: "",
      status: "",
      priority: "",
      assignee: "",
      category: "",
      startDate: "",
      dueDate: "",
      estimatedTime: "",
    })

    $("#addModal").modal('toggle')
  }

  onTrackerUpdate(event) {
    this.setState({tracker: event.target.value})
  }

  onSubjectUpdate(event) {
    this.setState({subject: event.target.value})
  }

  onDescriptionUpdate(event) {
    this.setState({description: event.target.value})
  }

  onStatusUpdate(event) {
    this.setState({status: event.target.value})
  }

  onPriorityUpdate(event) {
    this.setState({priority: event.target.value})
  }

  onAssigneeUpdate(event) {
    this.setState({assignee: event.target.value})
  }

  onCategoryUpdate(event) {
    this.setState({category: event.target.value})
  }

  onStartDateUpdate(event) {
    this.setState({startDate: event.target.value})
  }

  onDueDateUpdate(event) {
    this.setState({dueDate: event.target.value})
  }

  onEstimateUpdate(event) {
    this.setState({estimatedTime: event.target.value})
  }

  onEdit(event) {
    fetch(`http://localhost:8080/api/issues/`, {
      method: 'POST',
      headers: {'Authorization': `Bearer ${this.props.token}`, 'Content-Type': 'application/json'},
      body: JSON.stringify(this.state)
    })
      .then(this.props.onUpdate)
      .catch(console.log)

    event.preventDefault()
    $("#addModal").modal('toggle')
  }

  render() {
    return (
      <div>
        <button className="btn btn-primary" onClick={this.onShowEdit}>Add issue</button>
        <div className="modal fade" id="addModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="addModalLabel">New issue</h5>
                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div className="modal-body">
                <form onSubmit={this.handleSubmit}>
                  <div className="form-group">
                    <small>Tracker</small>
                    <input className="form-control" value={this.state.tracker}
                           onChange={this.onTrackerUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Subject</small>
                    <input className="form-control" value={this.state.subject}
                           onChange={this.onSubjectUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Description</small>
                    <input className="form-control" value={this.state.description}
                           onChange={this.onDescriptionUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Status</small>
                    <input className="form-control" value={this.state.status}
                           onChange={this.onStatusUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Priority</small>
                    <input className="form-control" value={this.state.priority}
                           onChange={this.onPriorityUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Assignee</small>
                    <input className="form-control" value={this.state.assignee}
                           onChange={this.onAssigneeUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Category</small>
                    <input className="form-control" value={this.state.category}
                           onChange={this.onCategoryUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Start date</small>
                    <input className="form-control" value={this.state.startDate}
                           onChange={this.onStartDateUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Due Date</small>
                    <input className="form-control" value={this.state.dueDate}
                           onChange={this.onDueDateUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Estimate</small>
                    <input className="form-control" value={this.state.estimatedTime}
                           onChange={this.onEstimateUpdate}/>
                  </div>
                </form>
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-primary" onClick={this.onEdit}>Add</button>
                <button type="button" className="btn btn-danger" data-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

class Issue extends Component {
  constructor(props) {
    super(props);

    this.state = {
      editedIssue: this.props.issue
    }

    this.onRemove = this.onRemove.bind(this);
  }

  onRemove() {
    fetch(`http://localhost:8080/api/${this.props.issue.id}`, {
      method: 'DELETE',
      headers: {'Authorization': `Bearer ${this.props.token}`},
    })
      .then(this.props.onUpdate)
      .catch(console.log)
  }

  render() {
    return (
        <tr key={this.props.issue.id}>
          <th scope="row">{this.props.index}</th>
          <td>{this.props.issue.tracker}</td>
          <td>{this.props.issue.subject}</td>
          <td>{this.props.issue.status}</td>
          <td>{this.props.issue.priority}</td>
          <td>{this.props.issue.assignee}</td>
          <td>{this.props.issue.category}</td>
          <td>{this.props.issue.dueDate}</td>
          <td>{this.props.issue.estimatedTime}</td>
          <td>
            <div className="d-inline-flex">
              <ShowIssue issue={this.props.issue} onUpdate={this.props.onUpdate} token={this.props.token}/>
              <EditIssue issue={this.props.issue} onUpdate={this.props.onUpdate} token={this.props.token}/>
              <button type="button" className="btn btn-danger btn-sm mr-1 float-right" onClick={this.onRemove}>
                <i className="fas fa-trash"/>
              </button>
            </div>
          </td>
        </tr>
    )
  }
}

class ShowIssue extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <button type="button" className="btn btn-primary btn-sm mr-1 float-right" data-toggle="modal"
                data-target={"#viewModal" + this.props.issue.id}>
          <i className="fas fa-search"/>
        </button>
        <div className="modal fade" id={"viewModal" + this.props.issue.id} tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="viewModalLabel">Issue {this.props.issue.id}</h5>
                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div className="modal-body">
                <small>Tracker</small>
                <p>{this.props.issue.tracker}</p>
                <small>Subject</small>
                <p>{this.props.issue.subject}</p>
                <small>Description</small>
                <p>{this.props.issue.description}</p>
                <small>Status</small>
                <p>{this.props.issue.status}</p>
                <small>Priority</small>
                <p>{this.props.issue.priority}</p>
                <small>Assignee</small>
                <p>{this.props.issue.assignee}</p>
                <small>Category</small>
                <p>{this.props.issue.category}</p>
                <small>Start date</small>
                <p>{this.props.issue.startDate}</p>
                <small>Due Date</small>
                <p>{this.props.issue.dueDate}</p>
                <small>Estimate</small>
                <p>{this.props.issue.estimatedTime}</p>
                <Comments comments={this.props.issue.comments} onUpdate={this.props.onUpdate} token={this.props.token} id={this.props.issue.id}/>
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-danger" data-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

class Comments extends Component {
  constructor(props) {
    super(props);

    this.state = {newComment: ""}
    this.onComment = this.onComment.bind(this)
  }

  onComment(event) {
    fetch(`http://localhost:8080/api/issues/${this.props.id}/comment`, {
      method: 'POST',
      headers: {'Authorization': `Bearer ${this.props.token}`},
      body: this.state.newComment
    })
      .then(this.props.onUpdate)
      .catch(console.log)
  }

  render() {
    let listing;

    if(this.props.comments.length === 0) {
      listing = (
        <div>
          <small>No comments</small>
          <p/>
        </div>
      )
    }
    else {
      listing = (
        <div>
          <small>Comments</small>
          {
            this.props.comments.map((comment) => {
              return <p>{comment}</p>
            })
          }
        </div>
      )
    }

    return (
      <div>
        {listing}
        <div className="d-inline-flex">
          <input className="form-control mr-1 float-right" value={this.state.category} onChange={(event) => this.setState({newComment: event.target.value})}/>
          <button className="btn btn-primary mr-1 float-right" onClick={this.onComment}>Comment</button>
        </div>
      </div>
    )
  }
}

class EditIssue extends Component {
  constructor(props) {
    super(props);

    this.state = {
      tracker: this.props.issue.tracker,
      subject: this.props.issue.subject,
      description: this.props.issue.description,
      status: this.props.issue.status,
      priority: this.props.issue.priority,
      assignee: this.props.issue.assignee,
      category: this.props.issue.category,
      startDate: this.props.issue.startDate,
      dueDate: this.props.issue.dueDate,
      estimatedTime: this.props.issue.estimatedTime,
    }

    this.onShowEdit = this.onShowEdit.bind(this);
    this.onTrackerUpdate = this.onTrackerUpdate.bind(this);
    this.onSubjectUpdate = this.onSubjectUpdate.bind(this);
    this.onDescriptionUpdate = this.onDescriptionUpdate.bind(this);
    this.onStatusUpdate = this.onStatusUpdate.bind(this);
    this.onPriorityUpdate = this.onPriorityUpdate.bind(this);
    this.onAssigneeUpdate = this.onAssigneeUpdate.bind(this);
    this.onCategoryUpdate = this.onCategoryUpdate.bind(this);
    this.onStartDateUpdate = this.onStartDateUpdate.bind(this);
    this.onDueDateUpdate = this.onDueDateUpdate.bind(this);
    this.onEstimateUpdate = this.onEstimateUpdate.bind(this);
    this.onEdit = this.onEdit.bind(this);
  }

  onShowEdit() {
    this.setState({
      tracker: this.props.issue.tracker,
      subject: this.props.issue.subject,
      description: this.props.issue.description,
      status: this.props.issue.status,
      priority: this.props.issue.priority,
      assignee: this.props.issue.assignee,
      category: this.props.issue.category,
      startDate: this.props.issue.startDate,
      dueDate: this.props.issue.dueDate,
      estimatedTime: this.props.issue.estimatedTime,
    })

    $("#editModal" + this.props.issue.id).modal('toggle')
  }

  onTrackerUpdate(event) {
    this.setState({tracker: event.target.value})
  }

  onSubjectUpdate(event) {
    this.setState({subject: event.target.value})
  }

  onDescriptionUpdate(event) {
    this.setState({description: event.target.value})
  }

  onStatusUpdate(event) {
    this.setState({status: event.target.value})
  }

  onPriorityUpdate(event) {
    this.setState({priority: event.target.value})
  }

  onAssigneeUpdate(event) {
    this.setState({assignee: event.target.value})
  }

  onCategoryUpdate(event) {
    this.setState({category: event.target.value})
  }

  onStartDateUpdate(event) {
    this.setState({startDate: event.target.value})
  }

  onDueDateUpdate(event) {
    this.setState({dueDate: event.target.value})
  }

  onEstimateUpdate(event) {
    this.setState({estimatedTime: event.target.value})
  }

  onEdit(event) {
    fetch(`http://localhost:8080/api/issues/${this.props.issue.id}`, {
      method: 'PUT',
      headers: {'Authorization': `Bearer ${this.props.token}`, 'Content-Type': 'application/json'},
      body: JSON.stringify(this.state)
    })
      .then(this.props.onUpdate)
      .catch(console.log)

    event.preventDefault()
    $("#editModal" + this.props.issue.id).modal('toggle')
  }

  render() {
    return (
      <div>
        <button type="button" className="btn btn-warning btn-sm mr-1 float-right" onClick={this.onShowEdit}>
          <i className="fas fa-edit"/>
        </button>
        <div className="modal fade" id={"editModal" + this.props.issue.id} tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="editModalLabel">Issue {this.state.id}</h5>
                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div className="modal-body">
                <form onSubmit={this.handleSubmit}>
                  <div className="form-group">
                    <small>Tracker</small>
                    <input className="form-control" value={this.state.tracker}
                           onChange={this.onTrackerUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Subject</small>
                    <input className="form-control" value={this.state.subject}
                           onChange={this.onSubjectUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Description</small>
                    <input className="form-control" value={this.state.description}
                           onChange={this.onDescriptionUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Status</small>
                    <input className="form-control" value={this.state.status}
                           onChange={this.onStatusUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Priority</small>
                    <input className="form-control" value={this.state.priority}
                           onChange={this.onPriorityUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Assignee</small>
                    <input className="form-control" value={this.state.assignee}
                           onChange={this.onAssigneeUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Category</small>
                    <input className="form-control" value={this.state.category}
                           onChange={this.onCategoryUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Start date</small>
                    <input className="form-control" value={this.state.startDate}
                           onChange={this.onStartDateUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Due Date</small>
                    <input className="form-control" value={this.state.dueDate}
                           onChange={this.onDueDateUpdate}/>
                  </div>
                  <div className="form-group">
                    <small>Estimate</small>
                    <input className="form-control" value={this.state.estimatedTime}
                           onChange={this.onEstimateUpdate}/>
                  </div>
                </form>
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-primary" onClick={this.onEdit}>Edit</button>
                <button type="button" className="btn btn-danger" data-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default App;
