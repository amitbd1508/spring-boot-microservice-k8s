kubectl get pod  -l 'app=account' --no-headers | awk '{print $1}' | xargs -I {} sh -c 'echo {}; kubectl logs --follow {}'
