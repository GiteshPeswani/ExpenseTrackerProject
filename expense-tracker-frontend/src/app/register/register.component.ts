import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
// Register ke liye data object
  registerObj: any = {
    fullName: '',
    email: '',
    password: '',
    confirmPassword: ''
  };

  // Backend API URL (Apne register route ke hisaab se change karein)
  apiUrl: string = 'http://localhost:8088/register';

  constructor(private http: HttpClient, private router: Router) {}

onRegister() {
    console.log("Button Clicked!");
    
    // 1. Basic Validation
    if (!this.registerObj.fullName || !this.registerObj.email || !this.registerObj.password) {
      alert('Bhai, saari fields bharna zaroori hai!');
      return;
    }

    // 2. Password Match Check
    if (this.registerObj.password !== this.registerObj.confirmPassword) {
      alert('Password aur Confirm Password match nahi ho rahe!');
      return;
    }

    // 3. Mapping data to match Java User Model
    // Java Model mein 'name' hai, frontend mein 'fullName'
    const dataToSend = {
      name: this.registerObj.fullName, // Frontend fullName -> Backend name
      email: this.registerObj.email,
      password: this.registerObj.password,
      username: this.registerObj.email, // Agar username email hi rakhna hai
      activeYn: 1 // Default active
    };

    console.log("Sending fixed data to backend:", dataToSend);

    // Yahan 'this.registerObj' ki jagah 'dataToSend' bhein
    this.http.post(this.apiUrl, dataToSend).subscribe({
      next: (res: any) => {
        console.log('Registration Success:', res);
        alert('Account ban gaya! Ab login kijiye.');
        this.router.navigateByUrl('/login');
      },
      error: (err) => {
        console.error('Registration Error:', err);
        // Backend 'body' key mein message bhej raha hai
        alert(err.error?.body || 'Registration fail ho gaya.');
      }
    });
}
}

