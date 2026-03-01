import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
      apiUrl: string = 'http://localhost:8088';
      LoginUrl: string = 'http://localhost:8088/login';


  constructor(private httpClient:HttpClient) { }

  savetobackend(gettingcategory:any):Observable<any>{
    console.log('gettingcategory:',gettingcategory)
    return this.httpClient.post(`${this.apiUrl}/category`,gettingcategory);
  }
  logincallkaro(email:any,password:any):Observable<any>{
    console.log(email);
    console.log(password);
    
    const body=new HttpParams()
    .set('email',email)
    .set('password',password)
    return this.httpClient.post(this.LoginUrl,body.toString(),{
      headers:new HttpHeaders().set('Content-Type','application/x-www-form-urlencoded'),
      withCredentials:true


    });
  }

}
