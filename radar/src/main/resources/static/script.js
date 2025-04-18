const canvas = document.getElementById('radarCanvas');
const ctx = canvas.getContext('2d');
const centerX = canvas.width / 2;
const centerY = canvas.height; // Base inferior do canvas
const maxRadius = canvas.width / 2;

function drawRadar() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);

  // Círculos concêntricos (semicírculo)
  ctx.strokeStyle = '#0f0';
  ctx.lineWidth = 1;

  for (let r = 50; r <= maxRadius; r += 50) {
    ctx.beginPath();
    ctx.arc(centerX, centerY, r, Math.PI, 0); // semicírculo
    ctx.stroke();
  }

  // Linhas radiais de 0 a 180 graus
  for (let angle = 0; angle <= 180; angle += 15) {
    const rad = angle * Math.PI / 180;
    const x = centerX + maxRadius * Math.cos(rad);
    const y = centerY - maxRadius * Math.sin(rad);
    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(x, y);
    ctx.stroke();
  }
}

// Converte polar (0–180°) para cartesianas no semicírculo
function polarToCartesian(angleDeg, distance) {
  const angleRad = angleDeg * Math.PI / 180;
  const x = centerX + distance * Math.cos(angleRad);
  const y = centerY - distance * Math.sin(angleRad);
  return { x, y };
}

// Desenha a detecção
function drawDetection(angle, distance) {
  drawRadar();

  const normalizedDistance = Math.min(distance, maxRadius);
  const { x, y } = polarToCartesian(angle, normalizedDistance);

  // Linha de varredura
  ctx.strokeStyle = 'lime';
  ctx.beginPath();
  ctx.moveTo(centerX, centerY);
  ctx.lineTo(x, y);
  ctx.stroke();

  // Ponto detectado
  ctx.fillStyle = 'red';
  ctx.beginPath();
  ctx.arc(x, y, 5, 0, 2 * Math.PI);
  ctx.fill();
}

// Simula dados vindo do backend (0–180 graus)
let angle = 0;
let direction = 1;
async function fetchRadarData() {
  try {
    const response = await fetch('/medidas');
    if (!response.ok) throw new Error("Erro na resposta");

    const data = await response.json();
    const angle = data.angle;
    const distance = data.distance;

    drawDetection(angle, distance); // usa os dados recebidos para desenhar

  } catch (error) {
    console.error("Erro ao buscar dados do radar:", error);
  }
}

// Atualiza a cada 200ms (ou como preferir)
setInterval(fetchRadarData, 200);
