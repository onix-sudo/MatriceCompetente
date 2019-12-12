var evalList = [];
var dataList = [];
var finalData= [];
//var names = [];

function pushEval(eval){
    evalList.push(eval);
}
function pushData(data){
    dataList.push(data);
}
function pushName(nm){
    names.push(nm);
}

function push(nm) {

    var newObject = {
      x: dataList,  // evaluari
      y: evalList, // data
      mode: 'lines+markers',
    };

    newObject.name = nm;
    finalData.push(newObject);

    evalList = [];
    dataList = [];
}


function plot(){

var layout = {
  title:'Istorie evaluari'
};

Plotly.newPlot('myDiv', finalData, layout);
}