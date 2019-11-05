var inp=document.getElementsByName('number');
for(var i=0; i<inp.length;i++){
    inp[i].addEventListener('input', function test(event){
        var test=event.currentTarget;
        test.value= test.value.replace(/\D/g, "");
    });
}



