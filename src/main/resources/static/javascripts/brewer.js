var Brewer = Brewer || {};

Brewer.MaskMoney = (function(){
	
	function MaskMoney(){
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');
	}
	
	MaskMoney.prototype.executar = function(){
		this.decimal.maskMoney({thousands:'.', decimal:','});
		this.plain.maskMoney({precision: 0, thousands:'.' });
	}
	
	return MaskMoney;
	
}());

$(function(){
	
 	var maskMoney = new Brewer.MaskMoney();
 	maskMoney.executar();

});