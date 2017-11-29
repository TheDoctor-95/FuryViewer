/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FuryViewerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ChapterSeenDetailComponent } from '../../../../../../main/webapp/app/entities/chapter-seen/chapter-seen-detail.component';
import { ChapterSeenService } from '../../../../../../main/webapp/app/entities/chapter-seen/chapter-seen.service';
import { ChapterSeen } from '../../../../../../main/webapp/app/entities/chapter-seen/chapter-seen.model';

describe('Component Tests', () => {

    describe('ChapterSeen Management Detail Component', () => {
        let comp: ChapterSeenDetailComponent;
        let fixture: ComponentFixture<ChapterSeenDetailComponent>;
        let service: ChapterSeenService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FuryViewerTestModule],
                declarations: [ChapterSeenDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ChapterSeenService,
                    JhiEventManager
                ]
            }).overrideTemplate(ChapterSeenDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChapterSeenDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChapterSeenService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ChapterSeen(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.chapterSeen).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
