import {Component, OnInit} from '@angular/core';
import {TicketService} from '../ticket.service';
import {Ticket} from './ticket';

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrls: ['./tickets.component.sass']
})
export class TicketsComponent implements OnInit {

  tickets: Ticket[];
  selectedTicket: Ticket;

  constructor(private ticketService: TicketService) {
  }

  ngOnInit(): void {
    this.getAllTickets();
  }

  getAllTickets() {
    this.ticketService.getAllTickets()
      .subscribe(res => {
        console.log(res);
        this.tickets = res;
      });
  }

  onSelect(ticket: Ticket): void {
    this.selectedTicket = ticket;
  }

}
