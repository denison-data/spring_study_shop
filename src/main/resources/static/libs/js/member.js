function nextFocus2(arg, len, nextname) {

   if (arg.value.length == len) {

      nextname.focus ();
      return;
   }
}
function english_str_check() {
	var str = document.getElementById('U_ID');
	var regexp = /^[A-Za-z@]/i;
	if( !regexp.test(str) ) {
		alert("영문외 문자는 사용할 수 없습니다!!");
		document.all.U_ID.value = "";
		return false;
	}
}

function member_agree_check()
{
	var frm = document.all;
	if(frm.agree1[0].checked==false)
	{
		alert("이용약관을 읽고 동의하셔야 합니다.");
		return frm.agree1[0].checked=true;
	}
	if(frm.agree2[0].checked==false)
	{
		alert("개인정보 최급방식을 읽고 동의하셔야 합니다.");
		return frm.agree2[0].checked=true;
	}
	if(frm.agree3[0].checked==false)
	{
		alert("개인정보 수집 및 목적을 읽고 동의하셔야 합니다.");
		return frm.agree3[0].checked=true;
	}
	if(frm.agree4[0].checked==false)
	{
		alert("수집한 개인정보의 보유기간을 읽고 동의하셔야 합니다.");
		return frm.agree4[0].checked=true;
	}
	location.href="./join";
}
function member_agree_check1()
{
	var frm = document.all;

	if(frm.agree1.checked==false)
	{
		alert("이용약관을 읽고 동의하셔야 합니다.");
		return frm.agree1.checked=true;
	}
	if(frm.agree2.checked==false)
	{
		alert("개인정보 최급방식을 읽고 동의하셔야 합니다.");
		return frm.agree2.checked=true;
	}
	if(frm.agree3.checked==false)
	{
		alert("개인정보 수집 및 목적을 읽고 동의하셔야 합니다.");
		return frm.agree3.checked=true;
	}
	if(frm.agree4.checked==false)
	{
		alert("수집한 개인정보의 보유기간을 읽고 동의하셔야 합니다.");
		return frm.agree4.checked=true;
	}
	location.href="./join";
}


function member_agree_check2()
{
	var frm = document.all;
	if(frm.agree1[0].checked==false)
	{
		alert("개인정보 최급방식을 읽고 동의하셔야 합니다.");

		return frm.agree1[0].checked=true;
	}
	if(frm.agree2[0].checked==false)
	{
		alert("이용약관을 읽고 동의하셔야 합니다.");
		return frm.agree2[0].checked=true;
	}

	location.href="./join";
}
function chkRadio(value){
	var frm = document.all;

	if(value == "기타"){
	}
	else
	{
		member_join_etc_txt2(frm);
	}
}
function chkRadio2(value){
	var frm = document.all;
	if(value == "직접입력"){
	}
	else
	{
	//	member_join_etc_txt3(frm);
	}
}
function member_join_etc_txt(frms)
{
	var hs_prodtxt = document.all.hs_prod_txt;
	if(frms.checked==true)
	{
		hs_prodtxt.disabled=false;
		hs_prodtxt.focus();
	}
	else
	{

		hs_prodtxt.disabled=true;
		hs_prodtxt.value="";
	}
}
function mem_join_prod2(frms)
{
	var hs_prod2txt = document.all.hs_prod2_txt;
	if(frms.checked==true)
	{
		hs_prod2txt.disabled=false;
		hs_prod2txt.focus();
	}
	else
	{

		hs_prod2txt.disabled=true;
		hs_prod2txt.value="";
	}
}
function member_join_etc_txt2(frms)
{

	var ggtxt = document.all.gg_gomae_txt;

	if(frms.checked==true)
	{
		ggtxt.disabled=false;
		ggtxt.focus();
	}
	else
	{

		ggtxt.disabled=true;
		ggtxt.value="";
	}
}
function mem_place_where(frms)
{
	var placetxt = document.all.place_txt;

	if(frms.checked==true)
	{
		placetxt.disabled=false;
		placetxt.focus();
	}
	else
	{

		placetxt.disabled=true;
		placetxt.value="";
	}
}
function member_join_etccheck(frm,value)
{
	var weddings = document.all.wedding;
	var jobs = document.all.Job;

	var hs_prods = document.getElementsByName("hs_prod[]")
	var hs_prods2s = document.getElementsByName("hs_prod2[]")
	var hs_prod_txt = document.all.hs_prod_txt;
	var hs_prod2_txt = document.all.hs_prod2_txt;
	var places = document.getElementsByName("place[]")
	var place_txt = document.all.place_txt;
	var gg_gomaes = document.all.gg_gomae;
	var gg_gomae_txt = document.all.gg_gomae_txt;

	var your_ids = document.all.your_id;
	var familys = document.all.family;
	var childrens = document.all.children;

	if(frm.checked == true )
	{
		jobs.disabled = false;
		your_ids.disabled = false;
		for(var i=0;i<2;i++){
			weddings[i].disabled = false;
		}
		for(var i2=0;i2<4;i2++){
			familys[i2].disabled= false;
		}

		for(var i3=0;i3<4;i3++){
			childrens[i3].disabled= false;
		}

		for(var j=0;j<4;j++){
			gg_gomaes[j].disabled = false;

			if(gg_gomaes[3].checked==true)
			{

				member_join_etc_txt2(frm);
			}
		}

		for(var z=0;z<15;z++){
			hs_prods[z].disabled = false;
			if(hs_prods[14].checked==true)
			{
				member_join_etc_txt(frm);
			}
		}

		for(var z2=0;z2<15;z2++){
			hs_prods2s[z2].disabled = false;
			if(hs_prods2s[14].checked==true)
			{
				mem_join_prod2(frm);
			}
		}
		for(var k=0;k<6;k++){
			places[k].disabled=false;
			if(places[5].checked==true)
			{

				mem_place_where(frm);
			}
			//info_mails[k].disabled=false;
		}

	}
	else
	{
		jobs.disabled = true;
		your_ids.disabled= true;
		for(var i=0;i<2;i++){
			weddings[i].disabled = true;
		}
		for(var i2=0;i2<4;i2++){
			familys[i2].disabled= true;
		}
		for(var i3=0;i3<4;i3++){
			childrens[i3].disabled= true;
		}


		for(var j=0;j<4;j++){
			gg_gomaes[j].disabled = true;
		}
		gg_gomae_txt.disabled=true;

		for(var z=0;z<15;z++){
			hs_prods[z].disabled = true;
			hs_prods.disabled=true;
		}
		hs_prod_txt.disabled=true;

		for(var z2=0;z2<15;z2++){
			hs_prods2s[z2].disabled = true;
			hs_prods2s.disabled=true;
		}
		hs_prod2_txt.disabled=true;

		for(var k=0;k<6;k++){
			places[k].disabled=true;
			places.disabled=true;
			//info_mails[k].disabled=false;
		}
		place_txt.disabled=true;
	}

}

function setbirth() {
  no = document.Join.Reg_Num1.value;
  y = '19' + no.substr(0,2);
  m = parseInt(no.substr(2,2), 10) ;
  d = parseInt(no.substr(4,2), 10) ;

  for(i = 0; i < document.Join.Birth_year.length; i++) {
    if(document.Join.Birth_year.options[i].value == y) {
      document.Join.Birth_year.options[i].selected = true;
    }
  }

  for(i = 0; i < document.Join.Birth_month.length; i++) {
    if(document.Join.Birth_month.options[i].value == m) {
      document.Join.Birth_month.options[i].selected = true;
    }
  }

  for(i = 0; i < document.Join.Birth_day.length; i++) {
    if(document.Join.Birth_day.options[i].value == d) {
      document.Join.Birth_day.options[i].selected = true;
    }
  }
}

function nextFocus(arg, len, nextname) {

   if (arg.value.length == len) {

      nextname.focus ();
      return;
   }
}

/* 공백제거 */
function trim(tmpStr)
{
	   var atChar;
	   if (tmpStr.length > 0)
	   atChar = tmpStr.charAt(0);
	   while (isSpace(atChar))
	   {
			tmpStr = tmpStr.substring(1, tmpStr.length);
			atChar = tmpStr.charAt(0);
	   }
	   if (tmpStr.length > 0)
	   atChar = tmpStr.charAt(tmpStr.length-1);
	   while (isSpace(atChar))
	   {
			tmpStr = tmpStr.substring(0,( tmpStr.length-1));
			atChar = tmpStr.charAt(tmpStr.length-1);
	   }
	   return tmpStr;
}
function isSpace(inChar)
{
  	return (inChar == ' ' || inChar == '\t' || inChar == '\n');
}

function talss(formName) {
	var returnChk = true;
	var chkResult = true;
	var defaultAlertText = '필수 입력 사항 입니다.';
	if(formName == undefined) {
		var obj = $('input, select, textarea');
	} else {
		var obj = $('form[name='+formName+'] input, form[name='+formName+'] select, form[name='+formName+'] textarea');
	}

	$(obj).each(function() {

		var alt = $(this).attr('alt'); //alt 속성

		switch($(this).attr('id')){
			case 'ims' :
				if(returnChk === false || chkResult === false) { return false; }

				if(trim($(this).val()) == '') {

					if(alt == '') {
						alert(defaultAlertText);
					} else {
						alert(alt);
					}
					$(this).focus();


					returnChk = false;
					return false;
				}
			break;

		}

	});


	if(returnChk === true && chkResult === true) {
			if(document.all.agree.checked==false)
			{
				alert('동의해야만 탈퇴를 진행할수 있습니다');
				return false;
			}

		$("#tals").submit();
	} else {

		return false;
	}
}


function CheckForm(formName) {
	alert('포트폴리오 페이지입니다.\n위 서비스는 더이상 이용하실 수 없습니다.\n');
	return false;
	var returnChk = true;
	var chkResult = true;
	var defaultAlertText = '필수 입력 사항 입니다.';
	var f=document.Join;

	if(formName == undefined) {
		var obj = $('input, select, textarea');
	} else {
		var obj = $('form[name='+formName+'] input, form[name='+formName+'] select, form[name='+formName+'] textarea');
	}

	$(obj).each(function() {

		var alt = $(this).attr('alt'); //alt 속성

		switch($(this).attr('id')){
			case 'im' :
				if(returnChk === false || chkResult === false) { return false; }

				if(trim($(this).val()) == '') {

					if(alt == '') {
						alert(defaultAlertText);
					} else {
						alert(alt);
					}
					$(this).focus();


					returnChk = false;
					return false;
				}
				if($(this).attr('name')=="U_ID")
				{
					if (hangul_chk(f.U_ID.value) != true ){
						alert("ID에 한글이나 여백은 사용할 수 없습니다.");
						f.U_ID.focus();
						chkResult = false;
						return false;
					}
					if (f.U_ID.value.length < 4 || f.U_ID.value.length > 10) {
						alert("ID는 4~10자리입니다.");
						chkResult = false;
						f.U_ID.focus();
						return false;
					}
				}
				if($(this).attr('name')=="U_Pass"  || $(this).attr('name')=="U_Pass2")
				{
					if($(this).attr('value').length < 4 || $(this).attr('value').length > 10)
					{
						alert("비밀번호는 4~ 10 자리 입니다.");
						chkResult = false;
						$(this).focus();
						return false;
					}
					if (f.U_Pass.value!==f.U_Pass2.value){
						alert("비밀번호가 일치하지 않습니다. 다시 입력하여 주십시요.");

						chkResult = false;
						f.U_Pass.focus();
						return false;
					}
				}

				if(f.Reg_Num1.value  && f.Reg_Num2.value)
				{
					var chk =0
					var yy = f.Reg_Num1.value.substring(0,2)
					var mm = f.Reg_Num1.value.substring(2,4)
					var dd = f.Reg_Num1.value.substring(4,6)
					var Sex = f.Reg_Num2.value.substring(0,1)
					if ((f.Reg_Num1.value.length!=6)||(yy <25||mm <1||mm>12||dd<1)){
							alert ("주민등록번호 를 바로 입력하여 주십시오.!");
							f.Reg_Num1.focus();
							chkResult = false;
							return (false);
					}

					if ((Sex != 1 && Sex !=2 )||(f.Reg_Num2.value.length != 7 )){
							alert ("주민등록번호를 바로 입력하여 주십시오.");
							f.Reg_Num2.focus();
							chkResult = false;
							return (false);
					}
					for (var i = 0; i <=5 ; i++){
						chk = chk + ((i%8+2) * parseInt(f.Reg_Num1.value.substring(i,i+1)))
					}

					for (var i = 6; i <=11 ; i++){
						chk = chk + ((i%8+2) * parseInt(f.Reg_Num2.value.substring(i-6,i-5)))
					}

					chk = 11 - (chk %11)
					chk = chk % 10
					if (chk != f.Reg_Num2.value.substring(6,7))
					{
							alert ("유효하지 않은 주민등록번호입니다.");
							f.Reg_Num1.focus();
							chkResult = false;
							return (false);
					}

				}
				if(chkResult === false) {
					$(this).focus();
				}

			break;

			case 'im2' :
				if(f.etc.checked==true)
				{
					if(returnChk === false || chkResult === false) { return false; }

					if(trim($(this).val()) == '') {
						if(returnChk === false || chkResult === false) { return false; }
						if(alt == '') {
							alert(defaultAlertText);
						} else {
							alert(alt);
						}
						$(this).focus();


						returnChk = false;
						return false;
					}
					/*
					if(f.drink[0].checked==false && f.drink[1].checked==false && f.drink[2].checked==false && f.drink[3].checked==false   )
					{
						alert("음주량을 체크해 주세요");
						chkResult = false;
						return false;
					}

					if(f.drink_day[0].checked==false && f.drink_day[1].checked==false && f.drink_day[2].checked==false && f.drink_day[3].checked==false   )
					{
						alert("음주횟수를 체크해 주세요");
						chkResult = false;
						return false;
					}
					*/
					if(f.wedding[0].checked==false && f.wedding[1].checked==false)
					{
						alert("결혼 여부 체크해 주세요");
						chkResult = false;
						return false;
					}
					if(f.gg_gomae[0].checked==false && f.gg_gomae[1].checked==false && f.gg_gomae[2].checked==false && f.gg_gomae[3].checked==false   )
					{
						alert("건강식품 구매주기 체크해 주세요");
						chkResult = false;
						return false;
					}

					if(f.family[0].checked==false && f.family[1].checked==false && f.family[2].checked==false && f.family[3].checked==false   )
					{
						alert("가족구성을  체크해 주세요");
						chkResult = false;
						return false;
					}
					if(f.children[0].checked==false && f.children[1].checked==false && f.children[2].checked==false && f.children[3].checked==false   )
					{
						alert("자녀여부를  체크해 주세요");
						chkResult = false;
						return false;
					}

					var hs_prods = document.getElementsByName("hs_prod[]")
					var m_check = false;

					var hs_prods2s = document.getElementsByName("hs_prod2[]")
					var m_check2 = false;

					var places = document.getElementsByName("place[]")
					var m_check3= false;

					for(idx=0;idx<hs_prods.length;idx++)
					{

						if(hs_prods[idx].checked==true)
						{
							m_check = true;
						}
					}

					if(m_check==false)
					{
						alert("건강식품을 체크해주세요");
						hs_prods[0].focus();
						chkResult = false;
						return false;
					}

					for(idx2=0;idx2<hs_prods2s.length;idx2++)
					{

						if(hs_prods2s[idx2].checked==true)
						{
							m_check2 = true;
						}
					}

					if(m_check2==false)
					{
						alert("필요로 하는 건강식품을 체크해주세요");
						hs_prods2s[0].focus();
						chkResult = false;
						return false;
					}

					for(idx3=0;idx3<places.length;idx3++)
					{

						if(places[idx3].checked==true)
						{
							m_check3 = true;
						}
					}

					if(m_check3==false)
					{
						alert("건강식품 구매하는 곳을 체크해주세요");
						places[0].focus();
						chkResult = false;
						return false;
					}

					/*
					if(f.mydrink_day[0].checked==false && f.mydrink_day[1].checked==false && f.mydrink_day[2].checked==false && f.mydrink_day[3].checked==false   && f.mydrink_day[4].checked==false )
					{
						alert("구매주기를 체크해 주세요");
						chkResult = false;
						return false;
					}
					*/

				}
			break;

		}

	});


	if(returnChk === true && chkResult === true) {

		$("#Join").submit();
	} else {

		return false;
	}
}


function hangul_chk(word) {
	var str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890-";

	for (i=0; i< word.length; i++)
	{
		idcheck = word.charAt(i);

		for ( j = 0 ;  j < str.length ; j++){

			if (idcheck == str.charAt(j)) break;

     			if (j+1 == str.length){
   				return false;
     			}
     		}
     	}
     	return true;
}

// 수정폼
function Zip_Search(ref) {
		var window_left = (screen.width-640)/2;
		var window_top = (screen.height-480)/2;
		window.open(ref,"checkIDWin",'width=450,height=300,status=no,scrollbars=yes,top=' + window_top + ',left=' + window_left + '');
}


function ID_chk(ref, form,text) {
	var ID = eval(Join.U_ID);

		if(Join.U_ID.value.length==0) {
			alert('아이디(ID)를 입력하신 후에 확인하세요!');
			Join.U_ID.focus();
			return;
		} else if(Join.U_ID.value.length<=3) {
			alert('4자리 이상 입력하신 후에 확인하세요!');
			Join.U_ID.focus();
			return;
		} else {
			ref = ref + "?U_ID=" + Join.U_ID.value + "&form="+form + "&idx=" +text;
			var window_left = (screen.width-640)/2;
			var window_top = (screen.height-480)/2;
			window.open(ref,"IDcheck",'width=475,height=350,status=no,top=' + window_top + ',left=' + window_left + '');
		}
}

function handlerNum()
{
    e = window.event;

	//숫자열 0 ~ 9 : 48 ~ 57, 키패드 0 ~ 9 : 96 ~ 105 ,8 : backspace, 46 : delete -->키코드값을 구분합니다. 저것들이 숫자랍니다.
    if(e.keyCode >= 48 && e.keyCode <= 57 || e.keyCode >= 96 && e.keyCode <= 105 || e.keyCode == 8 || e.keyCode == 46 || e.keyCode==9)
    {                                                                                                                        //delete나 backspace는 입력이 되어야되니까..
    }
    else
	{//숫자가 아니면 넣을수 없다.
        e.returnValue=false;
	}
}

function CheckForm_M(Join){
	alert('포트폴리오 페이지입니다.\n위 서비스는 더이상 이용하실 수 없습니다.\n');
	return false;
	if (Join.U_Pass_A.value==""){
		alert("비밀번호 찾기열쇠를 입력하여 주세요.");
		Join.U_Pass_A.focus();
		return false;
	}
	if (Join.U_Pass_A.value==""){
		alert("비밀번호 찾기열쇠를 입력하여 주세요.");
		Join.U_Pass_A.focus();
		return false;
	}
	if (Join.Email1.value==""){
		alert("이메일 주소를 입력하여 주십시요.");
		Join.Email1.focus();
		return false;
	}
	if (Join.Email2.value==""){
		alert("이메일 세부 주소를 입력하여 주십시요.");
		Join.Email2.focus();
		return false;
	}
	if (hangul_chk(Join.Email1.value) != true ){
		alert("이메일 주소에 한글이나 여백은 사용할 수 없습니다.");
		Join.Email.focus();
	 	return false;
	}
	/*
	if (hangul_chk(Join.Email2.value) != true ){
		alert("이메일 세부주소에 한글이나 여백은 사용할 수 없습니다.");
		Join.Email2.focus();
	 	return false;
	}
	*/
	if (Join.Mobile_1.value==""){
		alert("이동전화를 입력하셔야 합니다.");
		Join.Mobile_1.focus();
		return false;
	}
	if (Join.Mobile_2.value==""){
		alert("이동전화를 입력하셔야 합니다.");
		Join.Mobile_2.focus();
		return false;
	}
	if (Join.Mobile_3.value==""){
		alert("이동전화를 입력하셔야 합니다.");
		Join.Mobile_3.focus();
		return false;
	}

	if (Join.Zip1.value==""){
		alert("우편번호를 입력하여 주십시요.");
		Join.Zip1.focus();
		return false;
	}
	if (Join.Zip2.value==""){
		alert("우편번호를 입력하여 주십시요.");
		Join.Zip2.focus();
		return false;
	}

	if (Join.Address.value==""){
		alert("주소를 입력하여 주십시요.");
		Join.Address.focus();
		return false;
	}
	if (Join.Address_Ext.value==""){
		alert("세부주소를 입력하여 주십시요.");
		Join.Address_Ext.focus();
		return false;
	}
	if (Join.Phone_1.value==""){
		alert("연락처를 입력하셔야 합니다.");
		Join.Phone_1.focus();
		return false;
	}
	if (Join.Phone_2.value==""){
		alert("연락처를 입력하셔야 합니다.");
		Join.Phone_2.focus();
		return false;
	}
	if (Join.Phone_3.value==""){
		alert("연락처를 입력하셔야 합니다.");
		Join.Phone_3.focus();
		return false;
	}

	if(Join.etc.checked==true)
	{

		if(Join.Job.value=="")
		{
			alert("직업을 선택해 주세요");
			Join.Job.focus();
			return false;
		}
		/*
		if(Join.drink[0].checked==false && Join.drink[1].checked==false && Join.drink[2].checked==false && Join.drink[3].checked==false   )
		{
			alert("음주량을 체크해 주세요");
			return false;
		}

		if(Join.drink_day[0].checked==false && Join.drink_day[1].checked==false && Join.drink_day[2].checked==false && Join.drink_day[3].checked==false   )
		{
			alert("음주횟수를 체크해 주세요");
			return false;
		}
		*/

		if(Join.wedding[0].checked==false && Join.wedding[1].checked==false)
		{
			alert("결혼 여부 체크해 주세요");
			chkResult = false;
			return false;
		}
		if(Join.gg_gomae[0].checked==false && Join.gg_gomae[1].checked==false && Join.gg_gomae[2].checked==false && Join.gg_gomae[3].checked==false   )
		{
			alert("건강식품 구매주기 체크해 주세요");
			chkResult = false;
			return false;
		}
		//var mydrinks = document.all.mydrink;
		var hs_prods = document.getElementsByName("hs_prod[]")
		var m_check = false;

		var hs_prods2s = document.getElementsByName("hs_prod2[]")
		var m_check2 = false;

		var places = document.getElementsByName("place[]")
		var m_check3= false;

		for(idx=0;idx<hs_prods.length;idx++)
		{

			if(hs_prods[idx].checked==true)
			{
				m_check = true;
			}
		}

		for(idx2=0;idx2<hs_prods2s.length;idx2++)
		{

			if(hs_prods2s[idx2].checked==true)
			{
				m_check2 = true;
			}
		}

		for(idx3=0;idx3<places.length;idx3++)
		{

			if(places[idx3].checked==true)
			{
				m_check3 = true;
			}
		}


		if(m_check==false)
		{
			alert("본인 숙취 유형을 체크해주세요");
			mydrinks[0].focus();
			return false;
		}

		if(m_check2==false)
		{
			alert("필요로 하는 건강식품을 체크해주세요");
			hs_prods2s[0].focus();
			chkResult = false;
			return false;
		}

		if(m_check3==false)
		{
			alert("건강식품 구매하는 곳을 체크해주세요");
			places[0].focus();
			chkResult = false;
			return false;
		}


		/*
		if(Join.smoke[0].checked==false && Join.smoke[1].checked==false && Join.smoke[2].checked==false && Join.smoke[3].checked==false )
		{
			alert("흡연량 체크해 주세요");
			return false;
		}
		*/
		/*
		if(Join.marry[0].checked==false && Join.marry[1].checked==false)
		{
			alert("결혼 여부 체크해 주세요");
			return false;
		}

		if(Join.info_mail[0].checked==false && Join.info_mail[1].checked==false)
		{
			alert("정보수신 여부 체크해 주세요");
			return false;
		}
		*/
		if(Join.your_id.value== Join.U_ID.value)
		{
			alert("자기 자신은 추천받을수 없습니다.");
			return false;

		}

	}
	Join.submit();


}


function CheckFormcs(Join){
	if (trim(Join.U_Name.value)==""){
		alert("이름을 입력하여 주십시요.");
		Join.U_Name.focus();
		return false;
	}
   	for (var k = 0; k <= (Join.U_Name.value.length - 1); k++)
         	if (Join.U_Name.value.indexOf(" ") >= 0 ){
             		alert ("성명을 빈칸없이 붙여서 입력하여 주십시오");
             		Join.U_Name.focus();
             		return (false);
           	}
	if (trim(Join.U_ID.value)==""){
		alert("ID 를 입력하여 주십시요.");
		Join.U_ID.focus();
		return false;
	}
	if (hangul_chk(Join.U_ID.value) != true ){
		alert("ID에 한글이나 여백은 사용할 수 없습니다.");
		Join.U_ID.focus();
	 	return false;
	}
	if (Join.U_ID.value.length < 4 || Join.U_ID.value.length > 10) {
		alert("ID는 4~10자리입니다.");
		Join.U_ID.focus();
		return false;
	}
	if (Join.U_Pass.value==""){
		alert("비밀번호를 입력하여 주십시요.");
		Join.U_Pass.focus();
		return false;
	}
	if (hangul_chk(Join.U_Pass.value) != true ){
		alert("비밀번호에 한글이나 여백은 사용할 수 없습니다.");
		Join.U_Pass.focus();
	 	return false;
	}
	if (Join.U_Pass.value.length < 4 || Join.U_Pass.value.length > 10) {
		alert("비밀번호는 4~10자리입니다.");
		Join.U_Pass.focus();
		return false;
	}
	if (Join.U_Pass2.value==""){
		alert("비밀번호 확인을 입력하여 주십시요.");
		Join.U_Pass2.focus();
		return false;
	}
	if (Join.U_Pass.value!==Join.U_Pass2.value){
		alert("비밀번호가 일치하지 않습니다. 다시 입력하여 주십시요.");
		Join.U_Pass.focus();
		return false;
	}
	if (Join.U_Pass_Q.value==""){
		alert("비밀번호 찾기힌트를 입력하여 주세요.");
		Join.U_Pass_Q.focus();
		return false;
	}
	if (Join.U_Pass_A.value==""){
		alert("비밀번호 찾기열쇠를 입력하여 주세요.");
		Join.U_Pass_A.focus();
		return false;
	}
	if (Join.Email1.value==""){
		alert("이메일 주소를 입력하여 주십시요.");
		Join.Email1.focus();
		return false;
	}
	if (Join.Email2.value==""){
		alert("이메일 세부 주소를 입력하여 주십시요.");
		Join.Email2.focus();
		return false;
	}
	if (email_chk(Join.Email1.value) != true ){
		alert("이메일 주소에 한글이나 여백은 사용할 수 없습니다.");
		Join.Email.focus();
	 	return false;
	}
	if (email_chk(Join.Email2.value) != true ){
		alert("이메일 세부주소에 한글이나 여백은 사용할 수 없습니다.");
		Join.Email2.focus();
	 	return false;
	}
	// 주민등록번호 체크
	var chk =0
	var yy = Join.Reg_Num1.value.substring(0,2)
	var mm = Join.Reg_Num1.value.substring(2,4)
	var dd = Join.Reg_Num1.value.substring(4,6)
	var Sex = Join.Reg_Num2.value.substring(0,1)

 	if ((Join.Reg_Num1.value.length!=6)||(yy <25||mm <1||mm>12||dd<1)){
    		alert ("주민등록번호를 바로 입력하여 주십시오.");
    		Join.Reg_Num1.focus();
    		return (false);
  	}

  	if ((Sex != 1 && Sex !=2 )||(Join.Reg_Num2.value.length != 7 )){
    		alert ("주민등록번호를 바로 입력하여 주십시오.");
    		Join.Reg_Num2.focus();
    		return (false);
  	}

  	for (var i = 0; i <=5 ; i++){
		chk = chk + ((i%8+2) * parseInt(Join.Reg_Num1.value.substring(i,i+1)))
 	}

  	for (var i = 6; i <=11 ; i++){
        	chk = chk + ((i%8+2) * parseInt(Join.Reg_Num2.value.substring(i-6,i-5)))
 	}

  	chk = 11 - (chk %11)
  	chk = chk % 10

  	if (chk != Join.Reg_Num2.value.substring(6,7))
  	{
    		alert ("유효하지 않은 주민등록번호입니다.");
    		Join.Reg_Num1.focus();
    		return (false);
  	}
	// 주민등록번호 체크 끝
	if (Join.Mobile_1.value==""){
		alert("이동전화를 입력하셔야 합니다.");
		Join.Mobile_1.focus();
		return false;
	}
	if (Join.Mobile_2.value==""){
		alert("이동전화를 입력하셔야 합니다.");
		Join.Mobile_2.focus();
		return false;
	}
	if (Join.Mobile_3.value==""){
		alert("이동전화를 입력하셔야 합니다.");
		Join.Mobile_3.focus();
		return false;
	}

	if (Join.Zip1.value==""){
		alert("우편번호를 입력하여 주십시요.");
		Join.Zip1.focus();
		return false;
	}
	if (Join.Zip2.value==""){
		alert("우편번호를 입력하여 주십시요.");
		Join.Zip2.focus();
		return false;
	}
	if (Join.Address.value==""){
		alert("주소를 입력하여 주십시요.");
		Join.Address.focus();
		return false;
	}
	if (Join.Address_Ext.value==""){
		alert("세부주소를 입력하여 주십시요.");
		Join.Address_Ext.focus();
		return false;
	}
	if (Join.Phone_1.value==""){
		alert("연락처를 입력하셔야 합니다.");
		Join.Phone_1.focus();
		return false;
	}
	if (Join.Phone_2.value==""){
		alert("연락처를 입력하셔야 합니다.");
		Join.Phone_2.focus();
		return false;
	}
	if (Join.Phone_3.value==""){
		alert("연락처를 입력하셔야 합니다.");
		Join.Phone_3.focus();
		return false;
	}
	/*
	if (Join.U_ID.value == Join.Recommend.value){
		alert("추천인 아이디와 회원 아이디가 같습니다.");
		Join.Recommend.value = "";
		Join.Recommend.focus();
		return false;
	}
	*/

	if(Join.etc.checked==true)
	{

		if(Join.Job.value=="")
		{
			alert("직업을 선택해 주세요");
			Join.Job.focus();
			return false;
		}

		if(Join.drink[0].checked==false && Join.drink[1].checked==false && Join.drink[2].checked==false && Join.drink[3].checked==false   )
		{
			alert("음주량을 체크해 주세요");
			return false;
		}

		if(Join.drink_day[0].checked==false && Join.drink_day[1].checked==false && Join.drink_day[2].checked==false && Join.drink_day[3].checked==false   )
		{
			alert("음주횟수를 체크해 주세요");
			return false;
		}

		//var mydrinks = document.all.mydrink;
		var mydrinks = document.getElementsByName("mydrink[]")
		var m_check = false;

		for(idx=0;idx<mydrinks.length;idx++)
		{

			if(mydrinks[idx].checked==true)
			{
				m_check = true;
			}
		}

		if(m_check==false)
		{
			alert("본인 숙취 유형을 체크해주세요");
			mydrinks[0].focus();
			return false;
		}

		if(Join.mydrink_day[0].checked==false && Join.mydrink_day[1].checked==false && Join.mydrink_day[2].checked==false && Join.mydrink_day[3].checked==false   && Join.mydrink_day[4].checked==false )
		{
			alert("구매주기를 체크해 주세요");
			return false;
		}
		/*
		if(Join.smoke[0].checked==false && Join.smoke[1].checked==false && Join.smoke[2].checked==false && Join.smoke[3].checked==false )
		{
			alert("흡연량 체크해 주세요");
			return false;
		}
		*/
		if(Join.marry[0].checked==false && Join.marry[1].checked==false)
		{
			alert("결혼 여부 체크해 주세요");
			return false;
		}
		/*
		if(Join.info_mail[0].checked==false && Join.info_mail[1].checked==false)
		{
			alert("정보수신 여부 체크해 주세요");
			return false;
		}
		*/

	}
	Join.submit();
}

function search_date(gb)
{
	today = new Date();
	today_end = new Date();

	if(gb == '1month') today = new Date(today.valueOf()-(24*60*60*1000)*31);
	else if(gb == '3month') today = new Date(today.valueOf()-((24*60*60*1000)*92));
	else if(gb == '6month') today = new Date(today.valueOf()-((24*60*60*1000)*183));
	else if(gb == '1year') today = new Date(today.valueOf()-((24*60*60*1000)*365));

	ty = today.getFullYear();
	tm = today.getMonth()+1;
	td = today.getDate();

	ty_e = today_end.getFullYear();
	tm_e = today_end.getMonth()+1;
	td_e = today_end.getDate();

	if(tm<10)  tm = "0" + tm;
	if(td<10)  td = "0" + td;

	if(tm_e<10)  tm_e = "0" + tm_e;
	if(td_e<10)  td_e = "0" + td_e;

	for(i = 0; i < document.all.Year1.length; i++) {
		if(document.all.Year1.options[i].value == ty) {
			 document.all.Year1.options[i].selected = true;
		}
	}

	for(i = 0; i < document.all.Mon1.length; i++) {
		if(document.all.Mon1.options[i].value == tm) {
			 document.all.Mon1.options[i].selected = true;
		}
	}

	for(i = 0; i < document.all.Day1.length; i++) {
		if(document.all.Day1.options[i].value == td) {
			 document.all.Day1.options[i].selected = true;
		}
	}

	for(i = 0; i < document.all.Year2.length; i++) {
		if(document.all.Year2.options[i].value == ty_e) {
			 document.all.Year2.options[i].selected = true;
		}
	}

	for(i = 0; i < document.all.Mon2.length; i++) {
		if(document.all.Mon2.options[i].value == tm_e) {
			 document.all.Mon2.options[i].selected = true;
		}
	}

	for(i = 0; i < document.all.Day2.length; i++) {
		if(document.all.Day2.options[i].value == td_e) {
			 document.all.Day2.options[i].selected = true;
		}
	}
}

function Pro_Write(order, pid, uid,page)
{
	var window_left = (screen.width-640)/2;
	var window_top = (screen.height-480)/2;
	window.open("./pr_review.php?order="+order+"&Code="+pid+"&uid="+uid+"&page="+page,"상품평쓰기new","width=500,height=600,status=no,scrollbars=no,top="+window_top+", left="+window_left+"");
}

function chkMsgLength(intMax,objMsg,st) {
	var length = lengthMsg(objMsg.value);
	document.all.currentMsgLen.value = length;
	//st.innerHTML = length;//현재 byte수를 넣는다
	if (length > intMax) {
	  alert("최대 " + intMax + "byte이므로 초과된 글자수는 자동으로 삭제됩니다.");
	  objMsg.value = objMsg.value.replace(/rn$/, "");
	  objMsg.value = assertMsg(intMax,objMsg.value,st );
	}
}
function lengthMsg(objMsg) {
	var nbytes = 0;
	for (i=0; i<objMsg.length; i++) {
		var ch = objMsg.charAt(i);
		if(escape(ch).length > 4) {
			nbytes += 2;
		} else if (ch == 'n') {
			if (objMsg.charAt(i-1) != 'r') {
				nbytes += 1;
			}
		} else if (ch == '<' || ch == '>') {
			 nbytes += 4;
		} else {
			nbytes += 1;
		}
	}
	return nbytes;
}
function assertMsg(intMax,objMsg,st ) {
	var inc = 0;
	var nbytes = 0;
	var msg = "";

	var msglen = objMsg.length;
	for (i=0; i<msglen; i++) {
		var ch = objMsg.charAt(i);
		if (escape(ch).length > 4) {
			inc = 2;
		} else if (ch == 'n') {
			if (objMsg.charAt(i-1) != 'r') {
				inc = 1;
			}
		} else if (ch == '<' || ch == '>') {
			 inc = 4;
		} else {
			inc = 1;
		}
		if ((nbytes + inc) > intMax) {
			 break;
		}
		 nbytes += inc;
		 msg += ch;
	}
	document.all.currentMsgLen.value = nbytes; //현재 byte수를 넣는다
	return msg;
}

function preview_form()
{
	var Formsd = document.all;
	if (trim(Formsd.subject.value)==""){
		alert("제목을 입력하여 주십시요!~.");
		Formsd.subject.value="";
		Formsd.subject.focus();
		return false;
	}
	if (trim(Formsd.content.value)==""){
		alert("내용을 입력하여 주십시요!~.");
		Formsd.content.value="";
		Formsd.content.focus();
		return false;
	}
	document.formsd.submit();
}

function cks(obj){

  var elem = document.getElementsByName("one_check");
  var len = elem.length;

  if(obj.checked==true){
      for (var i=0; i<len; i++){
        if(elem[i].value==obj.value){
            elem[i].checked = true;
        }else{
            elem[i].checked = false;
        }
      }
  }else{
      for (var i=0; i<len; i++){
        if(elem[i].value==obj.value){
            elem[i].checked = false;
        }
      }
  }
}



function ck_all2()
{
	var frm =  document.form1;
	var chk_cnt = document.getElementsByName("one_check");

	var is_check = (frm.allcheck.checked)?true : false;
	if(chk_cnt.length==1)
	{
		frm.one_check.checked = is_check;

	}
	else
	{
	for(var i=0; i<chk_cnt.length; i++)
	frm.one_check[i].checked = is_check;
	}
}

function ck_all2_s()
{
	var frm =  document.form1_s;
	var chk_cnt = document.getElementsByName("one_check_s");

	var is_check = (frm.allcheck_s.checked)?true : false;
	if(chk_cnt.length==1)
	{
		frm.one_check_s.checked = is_check;

	}
	else
	{
	for(var i=0; i<chk_cnt.length; i++)
	frm.one_check[i].checked = is_check;
	}
}

function ck_all()
{

	var frm = document.form1;
	var is_check = (frm.allcheck.checked)?true : false;
	for(var i=0; i<frm.elements.length; i++)

	frm.elements[i].checked = is_check;
}

function sbmit(mode)
{
	var frm = document.form1;
	var chk_cnt = document.getElementsByName("one_check");
	var cnt = 0;
	var chk_data = "";


	if(chk_cnt.length ==0)
	{
		alert('조회된 데이터가 없습니다');
		return;
	}


	if(chk_cnt.length > 1) {
		for(i=0;i<chk_cnt.length;i++)
		{
			if(frm.one_check[i].checked==true)
			{
				cnt++;

				chk_data = chk_data + ',' + frm.one_check[i].value;
			}

		}
	}else {
		if(frm.one_check.checked==true) {
			cnt++;
			chk_data = frm.one_check.value;
		}
	}

	if(cnt==0){
		alert("선택된 데이터가 없습니다.");
		return;
	}	else {
		if(cnt>1)	chk_data = chk_data.substring(1, chk_data.length);
		frm.action = "od_p_01.php?order_nos="+chk_data+"&mode="+mode;
		document.form1.submit();
	}

}

function sbmit_s(mode)
{
	var frm = document.form1_s;
	var chk_cnt = document.getElementsByName("one_check_s");
	var cnt = 0;
	var chk_data = "";


	if(chk_cnt.length ==0)
	{
		alert('조회된 데이터가 없습니다');
		return;
	}


	if(chk_cnt.length > 1) {
		for(i=0;i<chk_cnt.length;i++)
		{
			if(frm.one_check_s[i].checked==true)
			{
				cnt++;

				chk_data = chk_data + ',' + frm.one_check_s[i].value;
			}

		}
	}else {
		if(frm.one_check_s.checked==true) {
			cnt++;
			chk_data = frm.one_check_s.value;
		}
	}

	if(cnt==0){
		alert("선택된 데이터가 없습니다.");
		return;
	}	else {
		if(cnt>1)	chk_data = chk_data.substring(1, chk_data.length);
		frm.action = "od_p_01s.php?order_nos="+chk_data+"&mode="+mode;
		document.form1_s.submit();
	}

}


function checkEnglishOnly( englishChar )

{
           if ( englishChar == null ) return false ;
           for( var i=0; i < englishChar.length;i++)

          {
                   var c=englishChar.charCodeAt(i);
                   if( !( (  65 <= c && c <= 90 ) || ( 97 <= c && c <= 122 ) ) )
                              return false ;
          }
          return true ;
}