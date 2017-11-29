/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ArtistTypeDetailComponent } from '../../../../../../main/webapp/app/entities/artist-type/artist-type-detail.component';
import { ArtistTypeService } from '../../../../../../main/webapp/app/entities/artist-type/artist-type.service';
import { ArtistType } from '../../../../../../main/webapp/app/entities/artist-type/artist-type.model';

describe('Component Tests', () => {

    describe('ArtistType Management Detail Component', () => {
        let comp: ArtistTypeDetailComponent;
        let fixture: ComponentFixture<ArtistTypeDetailComponent>;
        let service: ArtistTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [ArtistTypeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ArtistTypeService,
                    JhiEventManager
                ]
            }).overrideTemplate(ArtistTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArtistTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArtistTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ArtistType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.artistType).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
