# Docs solo en historial de commits

Esta carpeta está pensada para **guardar copias** de documentos que ya no están en el árbol actual del repo y que solo existen en commits anteriores.

## Cómo recuperar un doc del historial

Desde la **raíz del repo** (pipeline-as-code-with-jenkins-master):

```bash
# Ver el contenido
git show 5dfdc92:toolkit-fastflow/integrations/web-cuantica/docs/<nombre-del-archivo>

# Guardar en esta carpeta (docs/90-archivo/historial/)
git show 5dfdc92:toolkit-fastflow/integrations/web-cuantica/docs/<nombre-del-archivo> > docs/90-archivo/historial/<nombre-del-archivo>
```

## Lista de docs y commit

La lista completa de rutas y el commit de referencia está en **[COMPATIBILIDAD-Y-HISTORIAL.md](../../20-operaciones/COMPATIBILIDAD-Y-HISTORIAL.md)** (sección 2). Ahí se indica el commit `5dfdc92` y cada ruta para recuperar (AGENTES-FASTFLOW-REGISTRY-EMPAQUETADO.md, TAREA-FASTFLOW-REGISTRY-Y-EMPAQUETADO.md, HANDOFF-GEORGE-..., etc.).

No es obligatorio recuperar nada; solo cuando haga falta para referencia o pulido.
