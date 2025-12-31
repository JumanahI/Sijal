<!-- README.html (You can paste this directly into GitHub README.md — GitHub renders HTML) -->

<h1>Sijal (سِجال) – AI Interview Preparation Platform</h1>

<hr />

<h2>نبذة عن المشروع </h2>
<p>
<strong>سِجال</strong> منصة تساعد المستخدم على الاستعداد للمقابلات الوظيفية عبر جلسات مقابلة صوتية،
حيث يتم إنشاء جلسة مقابلة وتوليد أسئلة مخصصة (حسب السيرة الذاتية أو الوصف الوظيفي)،
ثم يتم حفظ التسجيل والنص (Transcript) وتحليل الإجابات باستخدام الذكاء الاصطناعي لإعطاء تقييم ونقاط قوة/ضعف.
</p>

<h2>Project Overview </h2>
<p>
<strong>Sijal</strong> is an AI-powered interview preparation platform that enables users to run voice-based mock interviews.
The system creates an interview session, generates tailored questions (from CV and/or job description),
collects call artifacts (recording + transcript), and produces AI-driven feedback including a final score,
strengths, and weaknesses.
</p>

<hr />

<h2>Team</h2>
<ul>
  <li>Muath</li>
  <li>Jumana</li>
  <li>Abdulmajed</li>
</ul>
<p>
<strong>Note:</strong> Database relationships/ERD were designed collaboratively by the whole team (not attributed to one person).
</p>

<hr />

<h2>Core Features</h2>
<ul>
   <li><strong>JWT Authentication:</strong> secure APIs with login and token-based access control</li>
  <li>Create interview sessions and generate questions based on CV and/or job description</li>
  <li>Voice interview simulation (phone call) with session validation</li>
  <li>Store interview recording and transcript after call ends</li>
  <li>AI interview analysis (final score + strengths + weaknesses)</li>
  <li><strong>HR Interview module:</strong> conduct interviews with HR and store HR evaluation</li>
  <li><strong>AI Improvement Plan:</strong> generate a personalized development plan for the user based on feedback</li>
  <li>Retrieve user sessions and session details with authorization (ownership checks)</li>
  <li>Health endpoint for deployment monitoring</li>
<li><strong>Customer Accounts:</strong> register and manage user profiles</li>
  <li><strong>CV PDF Template (Thymeleaf):</strong> render a professional resume HTML template used for PDF generation</li>
<li><strong>CV Management:</strong> upload CV PDF, extract text, and parse CV data via n8n workflow</li>
<li><strong>CV PDF Generation:</strong> generate and download a formatted CV as PDF</li>
<li><strong>Email Delivery:</strong> send generated CV PDF to a specified email address</li>
<li><strong>AI CV Suggestions:</strong> generate improvement recommendations for the user’s CV using OpenAI</li>
</ul>
<hr />

<h2>Muath’s Contributions</h2>
<ul>
  <li>Implemented Interview Session flow endpoints (start session, get sessions, get session by id, get questions payload)</li>
  <li>Implemented AI Analysis retrieval endpoints (per customer / per session with ownership check)</li>
  <li>Implemented Vapi webhook endpoint integration for handling end-of-call artifacts</li>
  <li>Implemented Questions endpoints (add/get/questions-for-session with authorization)</li>
  <li>Implemented a simple Health endpoint for environment checks</li>
  <li>
    Implemented OpenAI integration method:
    <ul>
      <li><code>OpenAiService.ask(prompt)</code> (only)</li>
    </ul>
  </li>
</ul>

<hr />

<h2>API Endpoints (Muath)</h2>

<h3>Health</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Auth</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>GET</td>
      <td><code>/health</code></td>
      <td>Server health check</td>
      <td>No</td>
    </tr>
  </tbody>
</table>

<h3>Interview Sessions</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Auth</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>POST</td>
      <td><code>/api/v1/interview-session/start-session-with-cv</code></td>
      <td>Create a session and generate questions using CV</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>POST</td>
      <td><code>/api/v1/interview-session/start-session-with-description</code></td>
      <td>Create a session and generate questions using job description</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/interview-session/get_question/{sessionId}</code></td>
      <td>Public payload for voice agent (valid + questions)</td>
      <td>No</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/interview-session/get-my-sessions</code></td>
      <td>Get all sessions for the authenticated user</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/interview-session/get-session-by-id/{sessionId}</code></td>
      <td>Get session details by id (ownership enforced)</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<h3>Questions</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Auth</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>POST</td>
      <td><code>/api/v1/questions/add</code></td>
      <td>Add a question</td>
      <td>(Project rules)</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/questions/get-all</code></td>
      <td>Get all questions</td>
      <td>(Project rules)</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/questions/questions-for-session/{sessionId}</code></td>
      <td>Get questions for a session (ownership enforced)</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<h3>AI Analysis</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Auth</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/analysis-by-ai/all-analysis</code></td>
      <td>Get all analyses for authenticated user sessions</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/analysis-by-ai/analysis-for-session/{sessionId}</code></td>
      <td>Get analysis for a specific session (ownership enforced)</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<h3>Vapi Webhook</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Auth</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>POST</td>
      <td><code>/api/v1/vapi/webhook</code></td>
      <td>Receive end-of-call report and process recording/transcript</td>
      <td>No (secured by webhook secret recommended)</td>
    </tr>
  </tbody>
</table>

<hr />

<h2>Services (Muath)</h2>
<table>
  <thead>
    <tr>
      <th>Service</th>
      <th>Main Responsibility</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code>InterviewSessionService</code></td>
      <td>Create session, generate questions, email session ID, return session payload, fetch user sessions</td>
    </tr>
    <tr>
      <td><code>QuestionService</code></td>
      <td>CRUD for questions + generate questions from CV / CV + job description</td>
    </tr>
    <tr>
      <td><code>RecordingInterviewService</code></td>
      <td>Handle Vapi webhook, store recording/transcript, trigger AI analysis</td>
    </tr>
    <tr>
      <td><code>InterviewAnalysisByAiService</code></td>
      <td>Return AI analysis DTOs (per user / per session) + save AI analysis for transcript</td>
    </tr>
    <tr>
      <td><code>OpenAiService</code></td>
      <td><strong>Muath contribution:</strong> <code>ask(prompt)</code> only</td>
    </tr>
  </tbody>
</table>

<hr />

<h2>Abdulmajed’s Contributions</h2>
<ul>
  <li>Implemented Spring Security configuration (JWT + roles/authorities integration)</li>
  <li>Implemented JWT Filter and password encoding (PasswordEncoder)</li>
  <li>Implemented Authentication flow (Login → JWT token)</li>
  <li>Implemented Customer module endpoints (register + account management)</li>
  <li>Implemented CV module (CRUD + “My CV” retrieval)</li>
  <li>Implemented CV upload flow (PDF upload → extract text → n8n parse → save CV)</li>
  <li>Implemented CV PDF generation endpoint (download as attachment)</li>
  <li>Implemented CV email sending endpoint (send generated CV PDF to recipient)</li>
  <li>Implemented OpenAI CV recommendations endpoint (cvImprovementSuggestions usage)</li>
  <li>Implemented CV PDF HTML template using Thymeleaf (professional resume layout used in PDF generation)</li>
  <li>Implemented the overall backend architecture and initialized the complete project scaffolding (packages/modules/folders) for the team</li>
<li>Implemented shared conventions and base layers (DTO IN/OUT, Advice/exception handling structure, validation groups, and Thymeleaf templates setup)</li>
</ul>

<hr />

<h2>API Endpoints (Abdulmajed)</h2>

<h3>Auth</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Auth</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>POST</td>
      <td><code>/api/v1/auth/login</code></td>
      <td>Login and return JWT token</td>
      <td>No</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/auth/test/role</code></td>
      <td>Return current user authorities (debug)</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<h3>Customer</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Auth</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>POST</td>
      <td><code>/api/v1/customer/register-customer</code></td>
      <td>Register a new customer</td>
      <td>No</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/customer/get-customers</code></td>
      <td>Get all customers</td>
      <td>(Project rules)</td>
    </tr>
    <tr>
      <td>PUT</td>
      <td><code>/api/v1/customer/update-customer</code></td>
      <td>Update authenticated customer</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>DELETE</td>
      <td><code>/api/v1/customer/delete-customer</code></td>
      <td>Delete authenticated customer</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<h3>CV</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Auth</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/cv/get-all-cv</code></td>
      <td>Get all CVs</td>
      <td>(Project rules)</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/cv/get-my-cv</code></td>
      <td>Get CV for authenticated user</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>POST</td>
      <td><code>/api/v1/cv/create-cv</code></td>
      <td>Create CV for authenticated user</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>PUT</td>
      <td><code>/api/v1/cv/update-cv</code></td>
      <td>Update CV for authenticated user</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>DELETE</td>
      <td><code>/api/v1/cv/delete-cv</code></td>
      <td>Delete CV for authenticated user</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/cv/get-my-cv</code></td>
      <td>Get CV for authenticated user</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<h3>CV Upload</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Auth</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>POST</td>
      <td><code>/api/v1/cv/upload-cv</code></td>
      <td>Upload CV PDF, extract text, parse via n8n, then save CV</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<h3>CV PDF</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Auth</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/cv/download-generate-cv</code></td>
      <td>Generate and download CV as PDF (attachment)</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<h3>CV Email</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Auth</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>POST</td>
      <td><code>/api/v1/cv/send-cv-to-email</code></td>
      <td>Send generated CV PDF to a recipient email</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<h3>AI (OpenAI)</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Auth</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/cv/get-recommendation</code></td>
      <td>Get CV improvement suggestions from AI</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<hr />

<h2>Services (Abdulmajed)</h2>
<table>
  <thead>
    <tr>
      <th>Service</th>
      <th>Main Responsibility</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code>CVService</code></td>
      <td>CV CRUD, upload+parse flow, and AI recommendations orchestration</td>
    </tr>
    <tr>
      <td><code>PDFParserService</code></td>
      <td>Extract text from uploaded CV PDF using PDFBox</td>
    </tr>
    <tr>
      <td><code>N8nIntegrationService</code></td>
      <td>Send CV text to n8n webhook and map parsed CV fields</td>
    </tr>
    <tr>
      <td><code>OpenAiService</code></td>
      <td>Generate CV improvement suggestions (cvImprovementSuggestions endpoint)</td>
    </tr>
    <tr>
      <td><code>CvPdfGeneratorService</code></td>
      <td>Generate CV PDF using Thymeleaf HTML template (professional resume layout)</td>
    </tr>
  </tbody>
</table>

<hr />

<h2>Templates (Abdulmajed)</h2>
<ul>
  <li><code>resume.html</code> (Thymeleaf): Professional Resume HTML template used for CV PDF generation</li>
</ul>

<hr />



<h2>Jumanah’s Contributions</h2>
<ul>
  <li>Implemented HR management module (register/update/delete/activate HR + ordered listing)</li>
  <li>Implemented HR interview lifecycle (start/update/cancel/end/delete + retrieval for customer/HR)</li>
  <li>Implemented HR analysis module (CRUD interview analysis)</li>
  <li>
    <strong>Implemented AI Development Plan endpoint</strong>
    <ul>
      <li>Generate AI-based development plan for customer based on HR interview analysis</li>
      <li>Endpoint: <code>GET /api/v1/Interview-analysis-by-hr/development-plan</code></li>
    </ul>
  </li>
  <li>Implemented HR rating module (add/update/delete + top rating + filters by HR/customer)</li>
  <li>Implemented interview request module (send/update/approve/reject/delete + list requests)</li>
  <li>Implemented subscription module (subscribe/get/cancel)</li>
  <li>Implemented payment status & callback endpoints</li>
  <li>Implemented card management endpoints (add/update/delete + get cards + get my cards)</li>
  <li>Implemented Jitsi meeting link creation service</li>
  <li>Implemented email service (basic email + attachments and CV sending)</li>
</ul>

<hr />

<h2>API Endpoints (Jumana)</h2>

<!-- HR MANAGEMENT -->
<h3>HR Management</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Auth</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>POST</td>
      <td><code>/api/v1/hr/register-hr</code></td>
      <td>Register HR</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>PUT</td>
      <td><code>/api/v1/hr/update-hr</code></td>
      <td>Update HR</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>DELETE</td>
      <td><code>/api/v1/hr/delete-hr</code></td>
      <td>Delete HR</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>PUT</td>
      <td><code>/api/v1/hr/activate-hr/{hr_id}</code></td>
      <td>Activate HR</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/hr/get-hr</code></td>
      <td>Get HR list</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/hr/get-hr-ordered</code></td>
      <td>Get ordered HR list</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<!-- INTERVIEW WITH HR -->
<h3>Interview With HR</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Auth</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>PUT</td>
      <td><code>/api/v1/Interview-with-hr/start-interview/{interview_id}</code></td>
      <td>Start HR interview</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>PUT</td>
      <td><code>/api/v1/Interview-with-hr/update-interview/{interview_id}</code></td>
      <td>Update HR interview</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>PUT</td>
      <td><code>/api/v1/Interview-with-hr/cancel-interview/{interview_id}</code></td>
      <td>Cancel HR interview</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>PUT</td>
      <td><code>/api/v1/Interview-with-hr/end-interview/{interview_id}</code></td>
      <td>End HR interview</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>DELETE</td>
      <td><code>/api/v1/Interview-with-hr/delete-interview/{interview_id}</code></td>
      <td>Delete HR interview</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/Interview-with-hr/get-interviews</code></td>
      <td>Get all HR interviews</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/Interview-with-hr/get-interview-by-customer</code></td>
      <td>Get HR interviews for customer</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/Interview-with-hr/get-interview-by-hr</code></td>
      <td>Get HR interviews for HR</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<!-- INTERVIEW ANALYSIS BY HR -->
<h3>Interview Analysis By HR</h3>
<p><strong>Base Path:</strong> <code>/api/v1/Interview-analysis-by-hr</code></p>

<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Auth</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>POST</td>
      <td><code>/add-interview-analysis/{interview_id}</code></td>
      <td>Add interview analysis (HR evaluation)</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>PUT</td>
      <td><code>/update-interview-analysis/{analysis_id}</code></td>
      <td>Update interview analysis</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>DELETE</td>
      <td><code>/delete-interview-analysis/{analysis_id}</code></td>
      <td>Delete interview analysis</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/get-interviews-analysis</code></td>
      <td>Get all interviews analysis</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/get-interview-analysis-by-customer</code></td>
      <td>Get interview analysis for customer</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/get-interview-analysis-by-hr</code></td>
      <td>Get interview analysis for HR</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/development-plan</code></td>
      <td>Get AI-driven development plan for customer</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<!-- HR RATING -->
<h3>HR Rating</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Auth</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>POST</td>
      <td><code>/api/v1/rating-hr/add-rating/{hr_id}</code></td>
      <td>Add rating for HR</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>PUT</td>
      <td><code>/api/v1/rating-hr/update-rating/{rating_id}</code></td>
      <td>Update rating</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>DELETE</td>
      <td><code>/api/v1/rating-hr/delete-rating/{rating_id}</code></td>
      <td>Delete rating</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/rating-hr/get-rating</code></td>
      <td>Get all ratings</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/rating-hr/get-top-rating</code></td>
      <td>Get top rated HR</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/rating-hr/get-rating-by-hr/{hr_id}</code></td>
      <td>Get ratings for HR</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/rating-hr/get-rating-by-customer</code></td>
      <td>Get customer ratings</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<!-- SERVICES -->
<hr />

<h2>Services (Jumana)</h2>
<table>
  <thead>
    <tr>
      <th>Service</th>
      <th>Main Responsibility</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code>JitsiService</code></td>
      <td>Create Jitsi room link (meeting link generation)</td>
    </tr>
    <tr>
      <td><code>SendMailService</code></td>
      <td>Send emails (simple message + with attachment + CV sending)</td>
    </tr>
  </tbody>
</table>

<hr />

<h2>Links</h2>
<ul>
  <li><strong>ERD:</strong> <a href="https://lucid.app/lucidchart/ed586add-f401-4cce-8977-6620e5f93367/edit?viewport_loc=-2615%2C-189%2C4427%2C1956%2C0_0&invitationId=inv_c1f6603d-7adb-4735-a2e4-e164ef1abef8">https://lucid.app/lucidchart/ed586add-f401-4cce-8977-6620e5f93367/edit?viewport_loc=-2615%2C-189%2C4427%2C1956%2C0_0&invitationId=inv_c1f6603d-7adb-4735-a2e4-e164ef1abef8</a></li>
  <li><strong>Postman Documentation:</strong> <a href="https://documenter.getpostman.com/view/51095397/2sBXVbJZNn">https://documenter.getpostman.com/view/51095397/2sBXVbJZNn</a></li>
  <li><strong>Figma:</strong> <a href="https://www.figma.com/design/NIJsffp2YQOJp0cQm8bale/Customizable-Online-Car-Repair-Garage-UI-Design---Fully-Editable-Dashboard-for-Desktop---Free-Downlo--Community-?node-id=0-1&t=wBFLfeHHZzeEQ0Hg-1">https://www.figma.com/design/NIJsffp2YQOJp0cQm8bale/Customizable-Online-Car-Repair-Garage-UI-Design---Fully-Editable-Dashboard-for-Desktop---Free-Downlo--Community-?node-id=0-1&t=wBFLfeHHZzeEQ0Hg-1</a></li>
  <li><strong>Domain:</strong> <a href="https://sijal.tech">https://sijal.tech</a></li>
</ul>
