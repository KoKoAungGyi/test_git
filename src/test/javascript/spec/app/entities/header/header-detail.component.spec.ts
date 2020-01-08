import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DemoTestModule } from '../../../test.module';
import { HeaderDetailComponent } from 'app/entities/header/header-detail.component';
import { Header } from 'app/shared/model/header.model';

describe('Component Tests', () => {
  describe('Header Management Detail Component', () => {
    let comp: HeaderDetailComponent;
    let fixture: ComponentFixture<HeaderDetailComponent>;
    const route = ({ data: of({ header: new Header(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DemoTestModule],
        declarations: [HeaderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HeaderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HeaderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load header on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.header).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
