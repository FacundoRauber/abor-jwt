import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { IntegranteService } from 'app/entities/integrante/integrante.service';
import { IIntegrante, Integrante } from 'app/shared/model/integrante.model';
import { Sexo } from 'app/shared/model/enumerations/sexo.model';

describe('Service Tests', () => {
  describe('Integrante Service', () => {
    let injector: TestBed;
    let service: IntegranteService;
    let httpMock: HttpTestingController;
    let elemDefault: IIntegrante;
    let expectedResult: IIntegrante | IIntegrante[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(IntegranteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Integrante(0, 0, 'AAAAAAA', 'AAAAAAA', currentDate, 0, Sexo.Hombre, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaNacimiento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Integrante', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaNacimiento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaNacimiento: currentDate
          },
          returnedFromService
        );
        service
          .create(new Integrante())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Integrante', () => {
        const returnedFromService = Object.assign(
          {
            dni: 1,
            apelllido: 'BBBBBB',
            nombre: 'BBBBBB',
            fechaNacimiento: currentDate.format(DATE_FORMAT),
            edad: 1,
            sexo: 'BBBBBB',
            estado: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaNacimiento: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Integrante', () => {
        const returnedFromService = Object.assign(
          {
            dni: 1,
            apelllido: 'BBBBBB',
            nombre: 'BBBBBB',
            fechaNacimiento: currentDate.format(DATE_FORMAT),
            edad: 1,
            sexo: 'BBBBBB',
            estado: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaNacimiento: currentDate
          },
          returnedFromService
        );
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Integrante', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
