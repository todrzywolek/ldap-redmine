import {Injectable} from '@angular/core';
import {AuthService} from './auth.service';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Ticket} from './tickets/ticket';
import {catchError} from 'rxjs/operators';
import {Error} from 'tslint/lib/error';

class Issue {
}

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  constructor(private auth: AuthService, private http: HttpClient) {
  }

  getAllTickets(): Observable<Ticket[]> {
    const headers = this.auth.getTokenHeader();
    return this.http.get<Ticket[]>('/api/issues', {headers})
      .pipe();
  }

  addTicket(ticket: Ticket) {
    const headers = this.auth.getTokenHeader();
    console.log('Sending ticket: ' + ticket);
    return this.http.post<Ticket>('/api/issues', ticket, {headers})
      .pipe(
        catchError((err => this.handleError(err)))
      );
  }

  private handleError(err: any): Observable<HttpResponse<any>> {
    console.log('Error in add Ticket');
    window.alert('Error during add ticket. Message: ' + err.message);
    throw new Error('error in source. Details: ' + err);
  }
}
