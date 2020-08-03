var EMaskList = EMaskList || {}

$(document).ready(function() {
});
var inputs = $('select,input');
for(var i=0; i < inputs.length; i++){
    console.log($(inputs[i]).attr('name'));
}