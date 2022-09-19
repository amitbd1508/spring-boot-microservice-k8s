kubectl get pod  -l 'app=payment' --no-headers | awk '{print $1}' | xargs -I {} sh -c 'echo {}; kubectl logs --follow {}'
