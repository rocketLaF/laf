window.onload = function(){

    onloadQues();

};


function onloadQues(){
    console.log("haha");
    var element = '<option>문제 유형 선택</option>' + 
                '<option value="mobile">휴대폰</option>' +
                '<option value="wallet">지갑</option>' +
                '<option value="bag">가방</option>';
    document.getElementById('quesQuery').innerHTML = element;

}
function selectBoxChange(val){
    console.log("you selected: " + val);
    if (val == "mobile"){
        document.getElementsByName("question")[0].setAttribute("value","배경화면은 무었입니까?");
        document.getElementsByName("answers")[0].setAttribute("value","기본바탕화면");
        document.getElementsByName("answers1")[0].setAttribute("value","색상");
        document.getElementsByName("answers2")[0].setAttribute("value","케릭터");
        document.getElementsByName("answers3")[0].setAttribute("value","인물사진");
        document.getElementsByName("answers4")[0].setAttribute("value","배경사진");
    }else if (val == "wallet") {
        document.getElementsByName("question")[0].setAttribute("value","지갑안에 없는 것은?");
        document.getElementsByName("answers")[0].setAttribute("value","주민등록증");
        document.getElementsByName("answers1")[0].setAttribute("value","현금");
        document.getElementsByName("answers2")[0].setAttribute("value","신용카드");
        document.getElementsByName("answers3")[0].setAttribute("value","남친사진");
        document.getElementsByName("answers4")[0].setAttribute("value","여친사진");
    }else if (val == "bag") {
        document.getElementsByName("question")[0].setAttribute("value","가방안에 없는 것은?");
        document.getElementsByName("answers")[0].setAttribute("value","강아지");
        document.getElementsByName("answers1")[0].setAttribute("value","지갑");
        document.getElementsByName("answers2")[0].setAttribute("value","현금");
        document.getElementsByName("answers3")[0].setAttribute("value","휴대폰");
        document.getElementsByName("answers4")[0].setAttribute("value","가디건");
    }else{
    }
}

function submitBtn(){
    var emptyRes = true;
    console.log(document.querySelector("#formTag").querySelectorAll("input"));
    for (const each of document.querySelector("#formTag").querySelectorAll("input")){
        if (each.id == "address") continue;
        if (each.value == ""){
            emptyRes = false;
        }
        console.log(each.value + "___" + each.id);
    }

    // document.querySelector("#formTag").querySelectorAll("input").forEach(function(each){  
    //     console.log(each.value + "___" + each.id);
    //     if (each.id == "address"){
            
    //     }
    //     if (each.value == ""){
    //         emptyRes = false;
    //     }
    // });
    if (emptyRes == false){
        alert("사진 및 모든항목을 입력하셔야 합니다");
    }

    return emptyRes;

}
