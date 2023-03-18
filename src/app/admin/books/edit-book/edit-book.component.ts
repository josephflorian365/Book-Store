import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../shared/book.service';

@Component({
  selector: 'app-edit-book',
  templateUrl: './edit-book.component.html'
})
export class EditBookComponent implements OnInit {
  errors?: string[];
  form?: FormGroup;
  bookId?: number;

  constructor(
    private fb: FormBuilder,
    private bookService: BookService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }


  ngOnInit(): void {
    this.bookId = parseInt(this.activatedRoute.snapshot.paramMap.get('id')!);

    this.bookService.get(this.bookId)
      .subscribe(book => {
        this.form = this.fb.group({
          title: [book.title, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
          slug: [book.slug, [Validators.required, Validators.pattern('[a-z0-9-]+')]],
          desc: [book.desc, [Validators.required]],
          price: [book.price, [Validators.required, Validators.min(0)]],
          coverPath: [book.coverPath, [Validators.required]],
          filePath: [book.filePath, [Validators.required]]
        })
      })

  }

  controlHasError(control: string, error: string): boolean {
    return this.form!.controls[control].hasError(error) && this.form!.controls[control].touched
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


  update() {
    if (this.form!.invalid) {
      this.form!.markAllAsTouched();
      return;
    }


    this.bookService.update(this.bookId!, this.form!.value)
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
