void keyPressed() {
  if (key == CODED) {
    if (keyCode == LEFT) {
      modeIndex = (int) Path.remainder(modeIndex-1, modes.length);
      createPath();
    } else if (keyCode == RIGHT) {
      modeIndex = (int) Path.remainder(modeIndex+1, modes.length);
      createPath();
    } else if (keyCode == SHIFT) {
      shift = true;
    }
  }
}

void keyReleased() {
  if (key == CODED) {
    if (keyCode == SHIFT) {
      shift = false;
    }
  } else if (key == ' ') {
    pathMode = (pathMode + 1) % 3;
    createPath();
  }
}

void mouseWheel(MouseEvent e) {
  modeIndex = (int) Path.remainder(modeIndex + e.getCount(), modes.length);
  createPath();
}

void mousePressed() {
  if (mouseButton == LEFT && !shift) {
    ab.set(mouseX, mouseY);
    mouseBehavior = "Moving " + abToString();
  } else if (mouseButton == RIGHT || (shift && mouseButton == LEFT)) {
    cd.set(mouseX, mouseY);
    mouseBehavior = "Moving " + cdToString();
  }
}

void mouseDragged() {
  if (mouseButton == LEFT && !shift) {
    ab.set(mouseX, mouseY);
    mouseBehavior = "Moving " + abToString();
  } else if (mouseButton == RIGHT || (shift && mouseButton == LEFT)) {
    cd.set(mouseX, mouseY);
    mouseBehavior = "Moving " + cdToString();
  }
}

void mouseReleased() {
  mouseBehavior = "";
}