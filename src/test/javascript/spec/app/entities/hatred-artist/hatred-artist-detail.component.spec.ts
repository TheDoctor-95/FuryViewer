/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HatredArtistDetailComponent } from '../../../../../../main/webapp/app/entities/hatred-artist/hatred-artist-detail.component';
import { HatredArtistService } from '../../../../../../main/webapp/app/entities/hatred-artist/hatred-artist.service';
import { HatredArtist } from '../../../../../../main/webapp/app/entities/hatred-artist/hatred-artist.model';

describe('Component Tests', () => {

    describe('HatredArtist Management Detail Component', () => {
        let comp: HatredArtistDetailComponent;
        let fixture: ComponentFixture<HatredArtistDetailComponent>;
        let service: HatredArtistService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [HatredArtistDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    HatredArtistService,
                    JhiEventManager
                ]
            }).overrideTemplate(HatredArtistDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HatredArtistDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HatredArtistService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new HatredArtist(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.hatredArtist).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
