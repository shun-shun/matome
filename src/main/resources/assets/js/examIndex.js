$(function(){
	$('tr[data-href]').addClass('clickable')

    //クリックイベント
    .click(function(elm) {

      //e.targetはクリックした要素自体、それがa要素以外であれば
      if(!$(elm.target).is('a')){

        //その要素の先祖要素で一番近いtrの
        //data-href属性の値に書かれているURLに遷移する
        window.location = $(elm.target).closest('tr').data('href');}
  });
});