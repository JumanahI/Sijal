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
<!-- ===================== JUMANA SECTION ===================== -->

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
  <li>Implemented email service (basic email)</li>
</ul>

<hr />

<h2>API Endpoints (Jumanah)</h2>

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
      <td><code>/api/v1/rating-hr/update-rating{rating_id}</code></td>
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

<!-- REQUEST INTERVIEW -->
<h3>Request Interview</h3>
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
      <td><code>/api/v1/request-interview/get-request</code></td>
      <td>Get all interview requests</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>POST</td>
      <td><code>/api/v1/request-interview/send-request/{hr_id}</code></td>
      <td>Send interview request to HR</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>PUT</td>
      <td><code>/api/v1/request-interview/update-request/{request_id}</code></td>
      <td>Update interview request</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>PUT</td>
      <td><code>/api/v1/request-interview/approve-request/{request_id}</code></td>
      <td>Approve request</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>PUT</td>
      <td><code>/api/v1/request-interview/reject-request/{request_id}</code></td>
      <td>Reject request</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>DELETE</td>
      <td><code>/api/v1/request-interview/delete-request/{request_id}</code></td>
      <td>Delete request</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<!-- SUBSCRIPTION -->
<h3>Subscription</h3>
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
      <td><code>/api/v1/subscription/subscribe</code></td>
      <td>Create subscription</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/subscription/get-subscription</code></td>
      <td>Get subscription</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>DELETE</td>
      <td><code>/api/v1/subscription/cancel-subscribe/{subscription_id}</code></td>
      <td>Cancel subscription</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<!-- CARDS -->
<h3>Cards</h3>
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
      <td><code>/api/v1/card/get-cards</code></td>
      <td>Get all cards</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>POST</td>
      <td><code>/api/v1/card/add-card</code></td>
      <td>Add card</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>PUT</td>
      <td><code>/api/v1/card/update-card/{card_id}</code></td>
      <td>Update card</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>DELETE</td>
      <td><code>/api/v1/card/delete-card/{card_id}</code></td>
      <td>Delete card</td>
      <td>Yes</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/card/get-my-cards</code></td>
      <td>Get my cards</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<!-- PAYMENTS -->
<h3>Payments</h3>
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
      <td><code>/api/v1/payments/get-status/{id}</code></td>
      <td>Get payment status</td>
      <td>No</td>
    </tr>
    <tr>
      <td>GET</td>
      <td><code>/api/v1/payments/callback</code></td>
      <td>Payment gateway callback</td>
      <td>No</td>
    </tr>
  </tbody>
</table>

<hr />

<h2>Services (Jumanah)</h2>
<table>
  <thead>
    <tr>
      <th>Service</th>
      <th>Main Responsibility</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code>HrService</code></td>
      <td>HR register/update/delete/activate + ordered listing</td>
    </tr>
    <tr>
      <td><code>InterviewWithHrService</code></td>
      <td>Start/update/cancel/end HR interviews + retrieve by HR/customer</td>
    </tr>
    <tr>
      <td><code>InterviewAnalysisByHrService</code></td>
      <td>CRUD HR interview analysis + generate development plan</td>
    </tr>
    <tr>
      <td><code>RatingHrService</code></td>
      <td>CRUD HR ratings + top rating + filters</td>
    </tr>
    <tr>
      <td><code>RequestInterviewService</code></td>
      <td>Send/update/approve/reject/delete interview requests</td>
    </tr>
    <tr>
      <td><code>SubscriptionService</code></td>
      <td>Subscribe/get/cancel subscription</td>
    </tr>
    <tr>
      <td><code>PaymentService</code></td>
      <td>Payment status retrieval</td>
    </tr>
    <tr>
      <td><code>CardService</code></td>
      <td>CRUD cards + get my cards</td>
    </tr>
    <tr>
      <td><code>JitsiService</code></td>
      <td>Create Jitsi room link (meeting link generation)</td>
    </tr>
    <tr>
      <td><code>SendMailService</code></td>
      <td>Send emails (simple message)</td>
    </tr>
  </tbody>
</table>

<hr />

<h2>Models (Jumanah)</h2>
<table>
  <thead>
    <tr>
      <th>Model</th>
      <th>Used In</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code>User</code></td>
      <td>Auth/ownership checks across HR modules</td>
    </tr>
    <tr>
      <td><code>InterviewWithHR</code></td>
      <td>HR interview lifecycle</td>
    </tr>
    <tr>
      <td><code>InterviewAnalysisByHR</code></td>
      <td>HR analysis + development plan</td>
    </tr>
    <tr>
      <td><code>RatingHr</code></td>
      <td>HR rating module</td>
    </tr>
    <tr>
      <td><code>RequestInterview</code></td>
      <td>Request interview module</td>
    </tr>
    <tr>
      <td><code>Card</code></td>
      <td>Card management</td>
    </tr>
    <tr>
      <td><code>CV</code></td>
      <td>Email service (basic email)</td>
    </tr>
  </tbody>
</table>

<hr />


<h2>Links</h2>
<ul>
  <li><strong>Use Case:</strong>
    <a href="https://lucid.app/lucidchart/0ac588ea-6c47-40ff-9f8c-2ddcc49f0a08/edit?invitationId=inv_dd8ca42c-dd44-4e7e-9f78-089609d925f1">
      https://lucid.app/lucidchart/0ac588ea-6c47-40ff-9f8c-2ddcc49f0a08/edit?invitationId=inv_dd8ca42c-dd44-4e7e-9f78-089609d925f1
    </a>
  </li>
  <li><strong>ERD:</strong>
    <a href="https://lucid.app/lucidchart/ed586add-f401-4cce-8977-6620e5f93367/edit?viewport_loc=-2615%2C-189%2C4427%2C1956%2C0_0&invitationId=inv_c1f6603d-7adb-4735-a2e4-e164ef1abef8">
      https://lucid.app/lucidchart/ed586add-f401-4cce-8977-6620e5f93367/edit?viewport_loc=-2615%2C-189%2C4427%2C1956%2C0_0&invitationId=inv_c1f6603d-7adb-4735-a2e4-e164ef1abef8
    </a>
  </li>
  <li><strong>Postman Documentation:</strong>
    <a href="https://documenter.getpostman.com/view/51095397/2sBXVbJZNn">
      https://documenter.getpostman.com/view/51095397/2sBXVbJZNn
    </a>
  </li>
  <li><strong>Figma:</strong>
    <a href="https://www.figma.com/design/NIJsffp2YQOJp0cQm8bale/Customizable-Online-Car-Repair-Garage-UI-Design---Fully-Editable-Dashboard-for-Desktop---Free-Downlo--Community-?node-id=0-1&t=wBFLfeHHZzeEQ0Hg-1">
      https://www.figma.com/design/NIJsffp2YQOJp0cQm8bale/Customizable-Online-Car-Repair-Garage-UI-Design---Fully-Editable-Dashboard-for-Desktop---Free-Downlo--Community-?node-id=0-1&t=wBFLfeHHZzeEQ0Hg-1
    </a>
  </li>
  <li><strong>Domain:</strong>
    <a href="https://sijal.tech">https://sijal.tech</a>
  </li>
</ul>

