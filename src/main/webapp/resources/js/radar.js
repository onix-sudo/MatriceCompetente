//jsVarList saves the name of the skills
//dataList saves the evaluations for each skill

var jsVarList = [];
var dataList = [];


function pushSkill(val, data) {
    jsVarList.push(val);
    dataList.push(data);
}

function plotRadar() {
    //the first values of the array are added at the end
    //so the points on the chart will form a closed geometric shape
    dataList.push(dataList[0]);
    jsVarList.push(jsVarList[0]);

    //the object that contains all the skills and evaluations passed into the chart plot function
    data = [{
      type: 'scatterpolar',
      r: dataList,
      theta: jsVarList,
      fill: 'toself'
      }
    ]

    //settings for radar chart display
    layout = {
      polar: {
        radialaxis: {
          visible: true,
          range: [0, 5]
        }
      },
      showlegend: false
    }

    Plotly.plot("chartDiv", data, layout, {showSendToCloud: true})

}