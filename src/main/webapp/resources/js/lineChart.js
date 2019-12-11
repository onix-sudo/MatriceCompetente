var evalList = [];
var dataList = [];

var tempEval = [];
var tempData = [];

var finalData= [];
var names = [];

function pushValues(eval, data, nm){

    evalList.push(eval);
    tempEval.push(eval);

    dataList.push(data);
    tempData.push(data);

    names.push(nm);

    console.log(names);
}

function push() {

    var newObject = {
      x: evalList, // data
      y: dataList,  // evaluari
      mode: 'lines+markers',
    };

for(var i =0; i<names.length; i++){

      if((names[i] === names[i+1])){

       var obj = {
             x:[tempEval[i], tempEval[i+1]], // tempEval[i] + tempEval[i+1], // data
             y: [tempData[i],tempData[i+1]], //tempData[i]+ tempData[i+1],  // evaluari
             mode: 'lines+markers',
             name: names[i]
           };

        console.log(tempEval[i] + tempEval[i+1]);
        finalData.push(obj);

      }
      newObject.name = names[i];
      }

    finalData.push(newObject);

    evalList = [];
    dataList = [];


}


function plot(){

var layout = {
  title:'Line and Scatter Plot'
};

Plotly.newPlot('myDiv', finalData, layout);
}