/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FavouriteSeriesDetailComponent } from '../../../../../../main/webapp/app/entities/favourite-series/favourite-series-detail.component';
import { FavouriteSeriesService } from '../../../../../../main/webapp/app/entities/favourite-series/favourite-series.service';
import { FavouriteSeries } from '../../../../../../main/webapp/app/entities/favourite-series/favourite-series.model';

describe('Component Tests', () => {

    describe('FavouriteSeries Management Detail Component', () => {
        let comp: FavouriteSeriesDetailComponent;
        let fixture: ComponentFixture<FavouriteSeriesDetailComponent>;
        let service: FavouriteSeriesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [FavouriteSeriesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FavouriteSeriesService,
                    JhiEventManager
                ]
            }).overrideTemplate(FavouriteSeriesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FavouriteSeriesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FavouriteSeriesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FavouriteSeries(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.favouriteSeries).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
