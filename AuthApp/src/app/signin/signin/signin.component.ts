import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthServiceService } from 'src/app/_services/AuthService/auth-service.service';
import { StorageSService } from 'src/app/_services/storageService/storage-s.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss']
})
export class SigninComponent implements OnInit {
  
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage ?:string[];
  roles: string[] = [];

  constructor(private authService:AuthServiceService,private storageSService:StorageSService,private router:Router) {}
  public LoginForm!: FormGroup;
  ngOnInit() {

    
    this.LoginForm = new FormGroup({
      username: new FormControl('', [
        Validators.required,
        Validators.minLength(4),
        Validators.maxLength(25),
      ]),
      password: new FormControl('', [Validators.required]),
    });

    if (this.storageSService.getToken() && this.storageSService.isLoggedIn() === true) {
     console.log(this.storageSService.getUser().privileges)
      this.isLoggedIn === true;
      this.isLoginFailed === false;
      this.roles = this.storageSService.getUser().roles;
      this.router.navigateByUrl('/Admin');
    }else{
      this.storageSService.signOut();
      this.router.navigate(['/Signin']);
      this.isLoggedIn===false
      this.isLoginFailed===true
   
    }
  }
  
  public hasError = (controlName: string, errorName: string) => {
    return this.LoginForm.controls[controlName].hasError(errorName);
  };

  onSubmit() {


    
    this.authService.Login(this.LoginForm.value).subscribe(
      (Response:any)=>{
      console.log(Response);
        this.storageSService.saveToken(Response.jwtAccessTocken);
        this.storageSService.saveRefreshToken(Response.jwtRefreshToken);
        this.storageSService.saveUser(Response);
        console.log(this.storageSService.getUser())
        this.isLoginFailed ===false;
        this.isLoggedIn === true;
        this.roles = this.storageSService.getUser().roles;
    
        if(this.storageSService.getToken() && this.storageSService.isLoggedIn() === true ){
          this.router.navigateByUrl('/Admin')
        }
        this.reloadPage();
      
      },
      (error)=>{

        this.isLoginFailed === true;
        this.isLoggedIn === false;
      
      }
    )
  }
 
  reloadPage(): void {
    window.location.reload();
  }
}