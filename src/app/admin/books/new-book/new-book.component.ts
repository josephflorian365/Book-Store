import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BookService } from '../shared/book.service';

@Component({
  selector: 'app-new-book',
  templateUrl: './new-book.component.html',
  styleUrls: ['./new-book.component.css']
})
export class NewBookComponent {
  errors?: string[];

  form: FormGroup = this.fb.group({
    title: [, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
    slug: [, [Validators.required, Validators.pattern('[a-z0-9-]+')]],
    desc: [, [Validators.required]],
    price: [, [Validators.required, Validators.min(0)]],
    coverPath: [, [Validators.required]],
    filePath: [, [Validators.required]]
  })

  constructor(
    private fb: FormBuilder,
    private bookService: BookService,
    private router: Router
  ) { }


  controlHasError(control: string, error: string): boolean {
    return this.form.controls[control].hasError(error) && this.form.controls[control].touched
  }

  uploadFile(event: any, control: string){
    const file = event.target.files[0];
    if(file){
      const formData = new FormData();
      formData.append('file', file);

      this.bookService.uploadFile(formData)
      .subscribe(response => {
        this.form!.controls[control].setValue(response.path);
      })
    }
  }

  createSlug() {
    const slug = this.form!.controls['title'].value
      .toLowerCase()
      .replace(/\s+/g, '-') // Replace spaces with -
      .replace(/[^\w\-]+/g, '') // Remove all non-word chars
      .replace(/\-\-+/g, '-') // Replace multiple - with single -
      .replace(/^-+/, '') // Trim - from start of text
      .replace(/-+$/, ''); // Trim - from end of text

    this.form!.controls['slug'].setValue(slug);
  }


  create() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }


    this.bookService.create(this.form.value)
      .subscribe({
        next: (book) => {
          this.router.navigate(['/admin/books']);
        },
        error: (error => {
          this.errors = error.error.errors;
        })
      })
  }

}
