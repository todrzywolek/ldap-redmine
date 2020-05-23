import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth.service';
import {HttpResponse} from '@angular/common/http';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  constructor(private Auth: AuthService, private router: Router) {
  }

  ngOnInit(): void {
  }

  loginUser(event) {
    event.preventDefault();
    const target = event.target;
    const username = target.querySelector('#username').value;
    const password = target.querySelector('#password').value;
    this.Auth.loginUser(username, password)
      .subscribe((res: HttpResponse<any>) => {
        const token = res.headers.get('Authorization');
        this.Auth.setLoggedInAndUserDetails(token, res.body);


        this.router.navigate(['home']);
      });
  }

}
