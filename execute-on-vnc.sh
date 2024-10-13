# Recommended by: https://joel-costigliola.github.io/assertj/assertj-swing-running.html
# Source: https://joel-costigliola.github.io/assertj/assertj-swing-running.html

# Configure VNC password otherwise doesn't access correctly the graphical environment
umask 0077
mkdir -p "$HOME/.vnc"
chmod go-rwx "$HOME/.vnc"
vncpasswd -f <<<"just-a-pw" >"$HOME/.vnc/passwd"

# Search an available display
NEW_DISPLAY=42
DONE="no"

while [ "$DONE" == "no" ]
do
  out=$(xdpyinfo -display :${NEW_DISPLAY} 2>&1)
  if [[ "$out" == name* ]] || [[ "$out" == Invalid* ]]
  then
    # command succeeded; or failed with access error;  display exists
    (( NEW_DISPLAY+=1 ))
  else
    # display doesn't exist
    DONE="yes"
  fi
done

echo "Using first available display :${NEW_DISPLAY}"

# Start VNC
OLD_DISPLAY=${DISPLAY}
vncserver ":${NEW_DISPLAY}" -localhost -geometry 1920x1080 -depth 16
export DISPLAY=:${NEW_DISPLAY}

# Start WM
# blackbox &

# Exec command
"$@"
EXIT_CODE=$?

# Stop VNC
export DISPLAY=${OLD_DISPLAY}
vncserver -kill ":${NEW_DISPLAY}"

exit $EXIT_CODE

