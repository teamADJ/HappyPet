package com.adj.happypet.Admin;

import androidx.fragment.app.Fragment;

public class ListFeedbackAdmin extends Fragment {
//
//    private RecyclerView recyclerViewListFeedback;
//    private FeedbackListAdapter feedbackListAdapter;
//    private List<Feedback_list> feedbackArrayLists;
//
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//    FirebaseUser fuser;
//    FirebaseFirestore db;
//    String userId, feedbackId;
//
//    public static Fragment newInstance(String param1, String param2) {
//        OrderFragment fragment = new OrderFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//
//    }
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//        fuser = FirebaseAuth.getInstance().getCurrentUser();
//        userId = fuser.getUid();
//        db = FirebaseFirestore.getInstance();
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_bottom_inbox_admin, viewGroup, false);
//
//        //        toolbar
//        Toolbar feedback_toolbar = v.findViewById(R.id.feedback_admin_toolbar);
//        ((BottomNavigationActivity) getActivity()).setSupportActionBar(feedback_toolbar);
//        ((BottomNavigationActivity) getActivity()).getSupportActionBar().setTitle("Feedback List");
//
//        feedbackArrayLists = new ArrayList<>();
//
//        recyclerViewListFeedback = v.findViewById(R.id.rvListFeedback);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerViewListFeedback.setLayoutManager(layoutManager);
//
//        feedbackListAdapter = new FeedbackListAdapter(this, feedbackArrayLists);
//        recyclerViewListFeedback.setAdapter(feedbackListAdapter);
//        getFeedbackList();
//        return v;
//    }
//
//    private void getFeedbackList() {
//
//        db.collection("Feedback").whereEqualTo("userId", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                feedbackArrayLists.clear();
//
//                for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                    Feedback_list feedback_list = new Feedback_list(
//                            documentSnapshot.getString("feedbackId"),
//                            documentSnapshot.getString("userId"),
//                            documentSnapshot.getString("email_admin"),
//                            documentSnapshot.getString("feedback")
//                    );
//                    feedbackArrayLists.add(feedback_list);
//                }
//
//                feedbackListAdapter.notifyDataSetChanged();
//
//            }
//        });
//
//    }
//

}
