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
export class LoginComponent implements OnInit {
  
 gettingcategory!:FormGroup
  constructor(private modalService:NgbModal,private apiService:ApiService){}
  loginform=new FormGroup({
    email:new FormControl('',Validators.required),
    password:new FormControl('',Validators.required),
  })
 
   ngOnInit(): void {
    this.gettingcategory = new FormGroup({
      userid: new FormControl(1), 
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      type: new FormControl('', Validators.required)
    });
  }
	closeResult: WritableSignal<string> = signal('');

	openModal(content: TemplateRef<any>) {
		this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
			(result) => {
				this.closeResult.set(`Closed with: ${result}`);
			},
			(reason) => {
				this.closeResult.set(`Dismissed ${this.getDismissReason(reason)}`);
			},
		);
	}

	private getDismissReason(reason: any): string {
		switch (reason) {
			case ModalDismissReasons.ESC:
				return 'by pressing ESC';
			case ModalDismissReasons.BACKDROP_CLICK:
				return 'by clicking on a backdrop';
			default:
				return `with: ${reason}`;
		}
	}
  logincallkaro(){
    const email=this.loginform.get('email')?.value
    const password=this.loginform.get('password')?.value

    this.apiService.logincallkaro(email,password)
  }
  //sidha form ko he bhej dege direct peche no need of making a array
  savetobackend(){
    this.apiService.savetobackend(this.gettingcategory.value).subscribe({
      next:(data)=>{
      console.log(data)
    },
    error:(err)=>{
      console.log('Error',err)
    }



  });
}
}
