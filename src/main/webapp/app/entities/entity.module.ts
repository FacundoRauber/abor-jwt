import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'vinculo-familiar',
        loadChildren: () => import('./vinculo-familiar/vinculo-familiar.module').then(m => m.Testmono04VinculoFamiliarModule)
      },
      {
        path: 'tipo-plan-asistencia',
        loadChildren: () => import('./tipo-plan-asistencia/tipo-plan-asistencia.module').then(m => m.Testmono04TipoPlanAsistenciaModule)
      },
      {
        path: 'nacionalidad',
        loadChildren: () => import('./nacionalidad/nacionalidad.module').then(m => m.Testmono04NacionalidadModule)
      },
      {
        path: 'nivel-educativo',
        loadChildren: () => import('./nivel-educativo/nivel-educativo.module').then(m => m.Testmono04NivelEducativoModule)
      },
      {
        path: 'tipo-ocupacion',
        loadChildren: () => import('./tipo-ocupacion/tipo-ocupacion.module').then(m => m.Testmono04TipoOcupacionModule)
      },
      {
        path: 'integrante',
        loadChildren: () => import('./integrante/integrante.module').then(m => m.Testmono04IntegranteModule)
      },
      {
        path: 'comunidad',
        loadChildren: () => import('./comunidad/comunidad.module').then(m => m.Testmono04ComunidadModule)
      },
      {
        path: 'origen-energia',
        loadChildren: () => import('./origen-energia/origen-energia.module').then(m => m.Testmono04OrigenEnergiaModule)
      },
      {
        path: 'origen-agua',
        loadChildren: () => import('./origen-agua/origen-agua.module').then(m => m.Testmono04OrigenAguaModule)
      },
      {
        path: 'tipo-vivienda',
        loadChildren: () => import('./tipo-vivienda/tipo-vivienda.module').then(m => m.Testmono04TipoViviendaModule)
      },
      {
        path: 'tipo-servicio',
        loadChildren: () => import('./tipo-servicio/tipo-servicio.module').then(m => m.Testmono04TipoServicioModule)
      },
      {
        path: 'tipo-tratamiento-basura',
        loadChildren: () =>
          import('./tipo-tratamiento-basura/tipo-tratamiento-basura.module').then(m => m.Testmono04TipoTratamientoBasuraModule)
      },
      {
        path: 'relevamiento',
        loadChildren: () => import('./relevamiento/relevamiento.module').then(m => m.Testmono04RelevamientoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class Testmono04EntityModule {}
