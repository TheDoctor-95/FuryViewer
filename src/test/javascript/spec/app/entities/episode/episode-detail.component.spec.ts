/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EpisodeDetailComponent } from '../../../../../../main/webapp/app/entities/episode/episode-detail.component';
import { EpisodeService } from '../../../../../../main/webapp/app/entities/episode/episode.service';
import { Episode } from '../../../../../../main/webapp/app/entities/episode/episode.model';

describe('Component Tests', () => {

    describe('Episode Management Detail Component', () => {
        let comp: EpisodeDetailComponent;
        let fixture: ComponentFixture<EpisodeDetailComponent>;
        let service: EpisodeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [EpisodeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EpisodeService,
                    JhiEventManager
                ]
            }).overrideTemplate(EpisodeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EpisodeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EpisodeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Episode(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.episode).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
