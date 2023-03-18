import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../shared/user.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html'
})
export class EditUserComponent implements OnInit {
  errors?: string[];
  form?: FormGroup;
  userId?: number;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }


  ngOnInit(): void {
    this.userId = parseInt(this.activatedRoute.snapshot.paramMap.get('id')!);

    this.userService.get(this.userId)
      .subscribe(user => {
        this.form = this.fb.group({
          firstName: [user.firstName, [Validators.required, Validators.minLength(3), Validators.maxLength(45), Validators.pattern(/^[a-zA-Z]+$/)]],
          lastName: [user.lastName, [Validators.required, Validators.minLength(3), Validators.maxLength(45), Validators.pattern(/^[a-zA-Z]+$/)]],
          fullName: [,],
          email: [user.email, [Validators.required, Validators.pattern(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/)]],
          password: [user.password, [Validators.required, Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/)]],
          role: [user.role, Validators.required]
        })
      })

  }

  controlHasError(control: string, error: string): boolean {
    return this.form!.controls[control].hasError(error)
  }


  update() {
    if (this.form!.invalid) {
      this.form!.markAllAsTouched();
      return;
    }

    const fullName = this.form!.value.firstName + ' ' + this.form!.value.lastName;
    this.form!.patchValue(
      {
        fullName: fullName
      },
      { emitEvent: false }
    );

    this.userService.update(this.userId!, this.form!.value)
      .subscribe({
        next: (user) => {
          this.router.navigate(['/admin/users']);
        },
        error: (error => {
          this.errors = error.error.errors;
        })
      })
  }

}
