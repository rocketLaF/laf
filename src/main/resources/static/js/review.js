window.onload = function(){
    console.log(typeof(option));
    console.log(typeof(score));
    
 
    if (option == 1){
        document.getElementById("fast").setAttribute("checked",true);
    }else if (option == 2){
        document.getElementById("timely").setAttribute("checked",true);
    }else{
        document.getElementById("safe").setAttribute("checked",true);
    }

    var scoreInt = parseInt(score);
    console.log(typeof(scoreInt));

    switch (scoreInt) {
        case 5:
            document.getElementById("5-stars").setAttribute("checked",true);
            break;
        case 4:
            document.getElementById("4-stars").setAttribute("checked",true);
            break;
        case 3:
            document.getElementById("3-stars").setAttribute("checked",true);
            break;
        case 2:
            document.getElementById("2-stars").setAttribute("checked",true);
            break;    
        case 1:
            document.getElementById("1-stars").setAttribute("checked",true);
            break;
        default:

    }

}