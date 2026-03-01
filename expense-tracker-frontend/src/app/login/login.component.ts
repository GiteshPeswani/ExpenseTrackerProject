import { HttpClient } from '@angular/common/http';
import { Component, OnInit, signal, TemplateRef, WritableSignal } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Route, Router } from '@angular/router';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{
   showPassword = false;
  isLoading = false;
  shakeForm = false;
  serverError = '';
  
 gettingcategory!:FormGroup
  constructor(private modalService:NgbModal,private apiService:ApiService){}
  loginform=new FormGroup({
    email:new FormControl('',Validators.required),
    password:new FormControl('',Validators.required),
  })
 
  
  logincallkaro(){
    const email=this.loginform.get('email')?.value
    const password=this.loginform.get('password')?.value

    this.apiService.logincallkaro(email,password).subscribe({
        next:(res:any)=>{
      alert("Login Successful");
      console.log("Response:",res);
    },

    error:(err)=>{
      alert("Invalid Email or Password");
      console.log("Error:",err);
    }
    })
    
    
  }
  
 isInvalid(field: string): boolean {
    const control = this.loginform.get(field);
    return !!(control && control.invalid && (control.dirty || control.touched));
  }
    getError(field: string): string {
    const control = this.loginform.get(field);
    if (!control || !control.errors) return '';
    if (control.errors['required']) return `${field === 'email' ? 'Email' : 'Password'} is required.`;
    if (control.errors['email']) return 'Please enter a valid email address.';
    if (control.errors['minlength']) return 'Password must be at least 8 characters.';
    return '';
  }


}
