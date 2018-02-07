/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ArtistBDetailComponent } from '../../../../../../main/webapp/app/entities/artist-b/artist-b-detail.component';
import { ArtistBService } from '../../../../../../main/webapp/app/entities/artist-b/artist-b.service';
import { ArtistB } from '../../../../../../main/webapp/app/entities/artist-b/artist-b.model';

describe('Component Tests', () => {

    describe('ArtistB Management Detail Component', () => {
        let comp: ArtistBDetailComponent;
        let fixture: ComponentFixture<ArtistBDetailComponent>;
        let service: ArtistBService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [ArtistBDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ArtistBService,
                    JhiEventManager
                ]
            }).overrideTemplate(ArtistBDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArtistBDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArtistBService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ArtistB(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.artistB).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
