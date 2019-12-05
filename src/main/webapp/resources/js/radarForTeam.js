var jsVarList = [];
var dataList = [];


function pushSkill(val, data) {
    console.log(val);
    jsVarList.push(val);
    console.log(jsVarList);
    dataList.push(data);
    console.log(dataList);
}


function plotRadar() {

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

function plotRadarTeam() {

data = [
  {
  type: 'scatterpolar',
  r: [1, 2, 3, 3, 2, 1],
  theta: ['A','B','C', 'D', 'E', 'A'],
  fill: 'none',
  name: 'Group A'
  },
  {
  type: 'scatterpolar',
  r: [2, 3, 2, 4, 3, 2],
  theta: ['A','B','C', 'D', 'E', 'A'],
  fill: 'none',
  name: 'Group B'
  },

  {
  type: 'scatterpolar',
  r: [1, 4, 2, 4, 4, 1],
  theta: ['A','B','C', 'D', 'E', 'A'],
  fill: 'none',
  name: 'Group C'
  },

  {
  type: 'scatterpolar',
  r: [2, 2, 2, 2, 1, 2],
  theta: ['A','B','C', 'D', 'E', 'A'],
  fill: 'none',
  name: 'Group D'
  },

  {
  type: 'scatterpolar',
  r: [4, 1, 2, 4, 1, 4],
  theta: ['A','B','C', 'D', 'E', 'A'],
  fill: 'none',
  name: 'Group E'
  }

]

layout = {
  polar: {
    radialaxis: {
      visible: true,
      range: [0, 5]
    }
  }
}

Plotly.plot("chartDiv", data, layout)

}