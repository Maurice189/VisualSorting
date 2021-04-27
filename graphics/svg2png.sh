#/bin/bash

OUT_DIR=$1

inkscape -z -h 30 "icons/bars.svg" -e "$OUT_DIR/bars.png"
inkscape -z -h 30 "icons/insert.svg" -e "$OUT_DIR/insert.png"
inkscape -z -h 30 "icons/start.svg" -e "$OUT_DIR/start.png"
inkscape -z -h 30 "icons/stop.svg" -e "$OUT_DIR/stop.png"
inkscape -z -h 30 "icons/step.svg" -e "$OUT_DIR/step.png"
inkscape -z -h 30 "icons/pause.svg" -e "$OUT_DIR/pause.png"
inkscape -z -h 30 "icons/timer.svg" -e "$OUT_DIR/timer.png"

inkscape -z -h 15 "icons/info.svg" -e "$OUT_DIR/info.png"
inkscape -z -h 15 "icons/info-rect.svg" -e "$OUT_DIR/info-rect.png"
inkscape -z -h 15 "icons/remove-rect.svg" -e "$OUT_DIR/remove-rect.png"
inkscape -z -h 15 "icons/timer.svg" -e "$OUT_DIR/timer-small.png"

# FIXME - The resulting PNGs are shifted
#inkscape -z -h 16 "logo.svg" -e "$OUT_DIR/logo.png"
#inkscape -z -h 32 "logo.svg" -e "$OUT_DIR/logo@2x.png"
#inkscape -z -h 64 "logo.svg" -e "$OUT_DIR/logo@3x.png"
#inkscape -z -h 128 "logo.svg" -e "$OUT_DIR/logo@4x.png"
#inkscape -z -h 256 "logo.svg" -e "$OUT_DIR/logo@5x.png"
#inkscape -z -h 512 "logo.svg" -e "$OUT_DIR/logo@6x.png"

inkscape -z -h 120 "banner.svg" -e "$OUT_DIR/banner.png"

