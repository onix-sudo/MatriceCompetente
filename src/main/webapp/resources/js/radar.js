var jsVarList = [];
var dataList = [];


function pushSkill(val, data) {
    jsVarList.push(val);
    dataList.push(data);
}


function plotRadar() {
    dataList.push(dataList[0]);
    jsVarList.push(jsVarList[0]);

    data = [{
      type: 'scatterpolar',
      r: dataList,
      theta: jsVarList,
      fill: 'toself'
      }
    ]

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