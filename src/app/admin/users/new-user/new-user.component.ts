import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../shared/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.css']
})
export class NewUserComponent {
  errors?: string[];

  form: FormGroup = this.fb.group({
    firstName: [, [Validators.required, Validators.minLength(3), Validators.maxLength(45), Validators.pattern(/^[a-zA-Z]+$/)]],
    lastName: [, [Validators.required, Validators.minLength(3), Validators.maxLength(45), Validators.pattern(/^[a-zA-Z]+$/)]],
    fullName: [,],
    email: [, [Validators.required, Validators.pattern(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/)]],
    password: [, [Validators.required, Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/)]],
    role: ['', Validators.required]
  })

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router
  ) { }


  controlHasError(control: string, error: string): boolean {
    return this.form.controls[control].hasError(error)
  }


  create() {
    
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    

    const fullName = this.form.value.firstName + ' ' + this.form.value.lastName;
    this.form.patchValue(
      {
        fullName: fullName
      },
      { emitEvent: false }
    );


    this.userService.create(this.form.value)
      .subscribe({
        next: (user) => {
          this.router.navigate(['/admin/users']);
        },
        error:  (error => {
          this.errors = error.error.errors;
          // Swal.fire({
          //   icon: 'error',
          //   title: 'Ha sucedido los siguientes errores:',
          //   html: '<ul>' +  this.errors?.map(error => `<li>${error}</li>`).join('') + '</ul>',
          // });
        })
      })
  }

  

}
