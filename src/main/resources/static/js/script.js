//change theme code start

let currentTheme=getTheme();
//intial-->

document.addEventListener('DOMContentLoaded',()=>{
    changeTheme();
})


//TODO:
function changeTheme(){
 document.querySelector('html').classList.add(currentTheme);

 changePageTheme(currentTheme,currentTheme);
 //set the listener to change theme button
 const changeThemeButton=document.querySelector('#theme_change_button')
 //change the text
 changeThemeButton.querySelector('span').textContent=currentTheme=="light"?"Dark":"Light";
 
 
 changeThemeButton.addEventListener('click',()=>{
   
  let oldTheme=currentTheme;
   if(currentTheme==="dark"){
      //theme ko light
     currentTheme="light";
   }else{
      //theme ko dark
      currentTheme="dark";
   }
     console.log(currentTheme);
     changePageTheme( currentTheme,oldTheme);
     });
}

//set theme to localstorage
function setTheme(theme){
    localStorage.setItem("theme",theme);
}

//get theme to localstorage
function getTheme(){
    let theme= localStorage.getItem("theme");
   
    return theme ? theme: "light";
  
   
}

//change current page theme
function changePageTheme(theme,oldTheme){

     //localstorage update
   setTheme(currentTheme);
   //remove the current theme
   if(oldTheme){
   
      document.querySelector('html').classList.remove(oldTheme);
   }
   //set the current theme
    document.querySelector('html').classList.add(theme);

    //change the text
    document.querySelector('#theme_change_button').querySelector('span').textContent=theme=="light"?"Dark":"Light";

}
//change theme code end