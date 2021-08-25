const targetDiv = document.getElementById('svg-target');
const svgNode= document.createElementNS('http://www.w3.org/2000/svg', 'svg');
svgNode.setAttributeNS(null, 'viewbox', '0 0 100 100');
targetDiv.appendChild(svgNode);

const circleNode = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
circleNode.setAttributeNS(null, 'cx', '50');
circleNode.setAttributeNS(null, 'cy', '50');
circleNode.setAttributeNS(null, 'r', '40');
circleNode.setAttributeNS(null, 'fill', 'blue');
svgNode.appendChild(circleNode);

const startButton = document.createElement( 'button');
startButton.setAttributeNS(null, 'id','start-button');
// startButton.innerHTML("Start");
targetDiv.appendChild(startButton);

const animateCircle = () => {
    let startTime = 0;
    const totalTime = 3000;
    const animateStep = (timestamp) => {
        if (!startTime) startTime = timestamp;
        //progress goes from 0 to 1 over 1s
        const progress = (timestamp - startTime)/totalTime;
        // console.log(progress);
        //move right 100px
        circleNode.setAttributeNS(null, 'cx', 50+(100*progress));
        circleNode.setAttributeNS(null, 'cy', 50+(100*progress))
        if(progress < 1) {
            window.requestAnimationFrame(animateStep);
            console.log("keep goin");

        }
    }
    window.requestAnimationFrame(animateStep);
    console.log("Last call");
};

document.getElementById('start-button').addEventListener('click', animateCircle);