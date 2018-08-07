$(function(){


	$(".acordioRoute tr th").click( function() {
		if($(this).parents('thead').find(".edit").val() != "完了" && !$(this).hasClass('editColomn')){
			$(this).parents('thead').next("tbody").toggle();
		}
	});


	$(".edit").click( function() {
		// ボタンテキストが完了だった場合
		if($(this).val() == "完了"){

			// ボタンテキストを編集に変更
			$(this).val("編集");

			$(this).parents('thead').find('.text').prop('readonly', true);

			// 押された設問の選択肢を変更不可能にする
			$(this).parents('thead').next('tbody').find(".area").each(function() {
				$(this).prop('readonly', true);

			});
		}
		// 編集だった場合
		else{

			if(!$(this).parents('thead').next("tbody").is(':visible')){
				$(this).parents('thead').next("tbody").toggle();
			}

			// ボタンテキストを編集に変更
			$(this).val("完了");

			$(this).parents('thead').find('.text').prop('readonly', false);

			// 押された設問の選択肢を変更可能にする
			$(this).parents('thead').next('tbody').find(".area").each(function() {
				$(this).prop('readonly', false);

			});
		}
	});
});