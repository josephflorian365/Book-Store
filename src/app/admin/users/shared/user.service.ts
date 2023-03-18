import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import Swal from 'sweetalert2';
import { User, UserPage } from './user.model';



@Injectable({
  providedIn: 'root'
})
export class UserService {
  errors?: string[];

  constructor(
    private http: HttpClient
  ) { }


  paginate(size: number = 5, page: number = 0): Observable<UserPage> {
    let params = new HttpParams();
    params = params.append('size', size);
    params = params.append('page', page);
    params = params.append('sort', 'createdAt,desc');

    return this.http.get<UserPage>('http://localhost:9090/api/admin/users', { params });
  }

  get(id: number): Observable<User> {
    return this.http.get<User>(`http://localhost:9090/api/admin/users/${id}`);
  }

  create(user: User): Observable<User> {
    return this.http.post<User>('http://localhost:9090/api/admin/users', user)
    .pipe(
      catchError((error) => {
        let errorMessage = this.getError(error);
        return throwError(errorMessage);
      })
    );
  }

  update(id: number, user: User) {
    return this.http.put<User>(`http://localhost:9090/api/admin/users/${id}`, user)
    .pipe(
      catchError((error) => {
        let errorMessage = this.getError(error);
        return throwError(errorMessage);
      }
    )
    );
  }

  delete(id: number) {
    return this.http.delete(`http://localhost:9090/api/admin/users/${id}`);
  }


  getAlert(error:string){
    // Swal.fire({
    //         icon: 'error',
    //         title: 'Ha sucedido los siguientes errores:',
    //         html: '<ul>' +  errors?.map(error => `<li>${error}</li>`).join('') + '</ul>',
    //       });
    Swal.fire({
      icon: 'error',
      title: 'Error',
      text: error
    });
  }

  getError(error:HttpClient): String{
    let errorMessage = 'Ocurrió un error en el usuario';
    console.log('catchError',error);

    if (error instanceof HttpErrorResponse) {
      console.log('error HttpErrorResponse',error);
      if (error.status === 400) {
        errorMessage = error.error.detail;
      }
    }
    this.getAlert(errorMessage);
    // Mostrar el mensaje de error en la interfaz de usuario
    console.error(errorMessage);
    return errorMessage;
  }
}
