var jsVarListTeam = [];
var dataListTeam = [];
var allData = [];


function pushSkillTeam(value, temp) {
    console.log(value);
    jsVarListTeam.push(value);
    console.log(jsVarListTeam);
    dataListTeam.push(temp);
    console.log(dataListTeam);
}


function plotRadarTeam() {

    layoutul = {
    title: 'Sales Growth',
      polar: {
        radialaxis: {
          visible: true,
          range: [0, 5]
        }
      },
      showlegend: true
    };

    Plotly.plot("chart", allData, layoutul);

}

//data = [
//  {
//  type: 'scatterpolar',
//  r: [1, 2, 3, 3, 2, 1],
//  theta: ['A','B','C', 'D', 'E', 'A'],
//  fill: 'none',
//  name: 'Group A'
//  }


function pushAllUsers(nume) {
    dataListTeam.push(dataListTeam[0]);
    jsVarListTeam.push(jsVarListTeam[0]);
    console.log(jsVarListTeam);
    var newObject = {
        type: 'scatterpolar',
        r: dataListTeam,
        theta: jsVarListTeam,
        fill: ''
    };

    console.log(nume);
    newObject.name = nume;

    allData.push(newObject);

    jsVarListTeam = [];
    dataListTeam = [];
}


//
//var jsVarList = [];
//var dataList = [];
//
//
//function pushSkill(val, data) {
//    console.log(val);
//    jsVarList.push(val);
//    console.log(jsVarList);
//    dataList.push(data);
//    console.log(dataList);
//}
//

//function plotRadarTeam() {
//
//
//data = [
//  {
//  type: 'scatterpolar',
//  r: [1, 2, 3, 3, 2, 1],
//  theta: ['A','B','C', 'D', 'E', 'A'],
//  fill: 'none',
//  name: 'Group A'
//  },
//  {
//  type: 'scatterpolar',
//  r: [2, 3, 2, 4, 3, 2],
//  theta: ['A','B','C', 'D', 'E', 'A'],
//  fill: 'none',
//  name: 'Group B'
//  },
//
//  {
//  type: 'scatterpolar',
//  r: [1, 4, 2, 4, 4, 1],
//  theta: ['A','B','C', 'D', 'E', 'A'],
//  fill: 'none',
//  name: 'Group C'
//  },
//
//  {
//  type: 'scatterpolar',
//  r: [2, 2, 2, 2, 1, 2],
//  theta: ['A','B','C', 'D', 'E', 'A'],
//  fill: 'none',
//  name: 'Group D'
//  },
//
//  {
//  type: 'scatterpolar',
//  r: [4, 1, 2, 4, 1, 4],
//  theta: ['A','B','C', 'D', 'E', 'A'],
//  fill: 'none',
//  name: 'Group E'
//  }
//
//]
//
//layout = {
//  polar: {
//    radialaxis: {
//      visible: true,
//      range: [0, 5]
//    }
//  }
//}
//
//Plotly.plot("chartDiv", data, layout)
//
//}