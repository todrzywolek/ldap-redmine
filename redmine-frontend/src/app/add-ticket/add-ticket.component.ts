import { Component, OnInit } from '@angular/core';
import {TicketService} from '../ticket.service';
import {Ticket} from '../tickets/ticket';

@Component({
  selector: 'app-add-ticket',
  templateUrl: './add-ticket.component.html',
  styleUrls: ['./add-ticket.component.sass']
})
export class AddTicketComponent implements OnInit {

  constructor(private ticketService: TicketService) { }

  ngOnInit(): void {
  }

  addTicket(event) {
    event.preventDefault();
    const target = event.target;
    console.log(target);
    let description = target.querySelector('#description').value;
    let assignee = target.querySelector('#assignee').value;
    const ticket: Ticket = {
      tracker: target.querySelector('#tracker').value,
      subject: target.querySelector('#subject').value,
      description: description.length > 0 ? description : '',
      status: target.querySelector('#status').value,
      category: target.querySelector('#category').value,
      priority: target.querySelector('#priority').value,
      assignee: assignee.length > 0 ? assignee : 'Unknown',
    };

    this.ticketService.addTicket(ticket)
      .subscribe(res => {
        console.log(res);
      });
  }
}
