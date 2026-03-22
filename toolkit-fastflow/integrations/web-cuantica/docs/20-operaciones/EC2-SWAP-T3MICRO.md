# Swap en EC2 t3.micro (paso del happy path)

En **t3.micro** (1 GiB RAM), el manual único incluye **2 GiB de swap** en la instancia Jenkins para dejar margen a Jenkins, Nginx y Maven. Es el mismo procedimiento que **§5.4** de **[GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md](../10-guia-unica/GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md)**.

---

## Pasos (en la EC2 Jenkins)

```bash
sudo dd if=/dev/zero of=/swapfile bs=1M count=2048 status=progress
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile
grep -q '/swapfile' /etc/fstab || echo '/swapfile swap swap defaults 0 0' | sudo tee -a /etc/fstab
free -h
```

Debes ver **Swap** distinto de **0B**.

---

## Persistente tras reinicio

La línea en `/etc/fstab` del bloque anterior mantiene el swap activo al reiniciar la EC2.

---

## Referencia

Visión global del laboratorio: **[REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md)**.
