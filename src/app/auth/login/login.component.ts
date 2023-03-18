import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Credentials } from '../shared/auth.model';
import { AuthService } from '../shared/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {

  credentials: Credentials = {
    email: '',
    password: ''
  };


  constructor(
    private authService: AuthService,
    private snackBar: MatSnackBar,
    private router: Router
  ) { }


  login(form: NgForm) {
    if (form.invalid) {
      return;
    }

    this.authService.login(this.credentials)
      .subscribe(user => {
        this.snackBar.open(`Bienvenido ${user.firstName}`, 'Cerrar', {
          duration: 5 * 1000,
          verticalPosition: 'bottom',
          horizontalPosition: 'center'
        });
        this.router.navigate(['/'])
      })
  }

}
