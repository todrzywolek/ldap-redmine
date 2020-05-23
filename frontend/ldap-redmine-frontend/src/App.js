import React, {Component} from 'react';

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
        this.setState({username: event.target.value});
    }

    handlePasswordChange(event) {
        this.setState({password: event.target.value});
    }

    handleSubmit(event) {
        console.log('Attempting to login')
        let credentials = btoa(`${this.state.username}:${this.state.password}`)
        fetch('http://localhost:8080/login', {method: 'POST', headers: {'Authorization': `Basic ${credentials}`}})
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
            <div class="w-25">
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
    }

    componentDidMount() {
        fetch('http://localhost:8080/issues', {
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
                    <button className="btn btn-primary">Add issue</button>
                </div>
                <table className="table table-sm">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Tracker</th>
                        <th scope="col">Subject</th>
                        <th scope="col">Description</th>
                        <th scope="col">Status</th>
                        <th scope="col">Priority</th>
                        <th scope="col">Assignee</th>
                        <th scope="col">Category</th>
                        <th scope="col">Start Date</th>
                        <th scope="col">Due Date</th>
                        <th scope="col">Estimate</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.issues.map((issue, index) => (
                            <tr>
                                <th scope="row">{index + 1}</th>
                                <td>{issue.tracker}</td>
                                <td>{issue.subject}</td>
                                <td>{issue.description}</td>
                                <td>{issue.status}</td>
                                <td>{issue.priority}</td>
                                <td>{issue.assignee}</td>
                                <td>{issue.category}</td>
                                <td>{issue.startDate}</td>
                                <td>{issue.dueDate}</td>
                                <td>{issue.estimatedTime}</td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </div>
        )
    }
}

export default App;
