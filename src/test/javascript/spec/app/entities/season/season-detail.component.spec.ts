/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SeasonDetailComponent } from '../../../../../../main/webapp/app/entities/season/season-detail.component';
import { SeasonService } from '../../../../../../main/webapp/app/entities/season/season.service';
import { Season } from '../../../../../../main/webapp/app/entities/season/season.model';

describe('Component Tests', () => {

    describe('Season Management Detail Component', () => {
        let comp: SeasonDetailComponent;
        let fixture: ComponentFixture<SeasonDetailComponent>;
        let service: SeasonService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [SeasonDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SeasonService,
                    JhiEventManager
                ]
            }).overrideTemplate(SeasonDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SeasonDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeasonService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Season(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.season).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
