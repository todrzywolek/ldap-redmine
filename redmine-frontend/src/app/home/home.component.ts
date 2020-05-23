import {Component, OnInit} from '@angular/core';
import {AuthService, UserDetails} from '../auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
})
export class HomeComponent implements OnInit {

  user: UserDetails;

  constructor(private auth: AuthService) {
  }

  ngOnInit(): void {
    this.user = this.auth.getUserDetails();
  }

  logout() {
    this.auth.logout();
    console.log('Logging out');
  }
}
