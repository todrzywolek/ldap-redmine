import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Error} from "tslint/lib/error";

export interface UserDetails {
  username: string;
  authorities: string[];
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loggedInStatus = 'loggedInStatus';
  private userDetails: UserDetails;

  constructor(private http: HttpClient) {
  }

  loginUser(username, password): Observable<HttpResponse<any>> {

    const headers = this.createAuthenticationHeader(username, password);
    return this.http.post('/api/login', {}, {headers, observe: 'response'})
      .pipe(
        catchError((err => this.handleError('loginUser', err)))
      );
  }

  private createAuthenticationHeader(username: string, password: string) {
    return new HttpHeaders({
      Authorization: 'Basic ' + btoa(username + ':' + password)
    });
  }

  private handleError(requestName: string, err): Observable<HttpResponse<any>> {
    console.log('Error in ' + requestName);
    this.logout();
    window.alert('Error during login. Message: ' + err.message);
    throw new Error('error in source. Details: ' + err);
  }

  isLoggedIn(): boolean {
    const loggedInStatus = localStorage.getItem(this.loggedInStatus);
    console.log(loggedInStatus);
    return loggedInStatus === null || loggedInStatus.length === 0 ? false : loggedInStatus === 'true';
  }

  setLoggedInAndUserDetails(token: string | null, userDetails: UserDetails) {
    localStorage.setItem('token', token);
    localStorage.setItem(this.loggedInStatus, 'true');
    this.userDetails = userDetails;
    console.log('User logged in successfully');
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.setItem(this.loggedInStatus, 'false');
  }

  getTokenHeader(): HttpHeaders {
    const token = 'Bearer ' + localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: token
    });
  }

  getUserDetails() {
    return this.userDetails;
  }
}
